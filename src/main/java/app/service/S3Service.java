package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.default-acl:private}")
    private String defaultAcl;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @PostConstruct
    public void init() {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    private void validateKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("La clave no puede estar vacía.");
        }
    }

    /**
     * Crear una carpeta en S3.
     */
    public void createFolder(String folderName) {
        validateKey(folderName);
        String folderKey = folderName.endsWith("/") ? folderName : folderName + "/";
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(folderKey).build(), RequestBody.empty());
    }

    /**
     * Subir un archivo desde un Path.
     */
    public String uploadFile(String folderName, Path filePath) throws IOException {
        validateKey(folderName);
        validateKey(filePath.toString());

        String fileKey = folderName.endsWith("/") ? folderName + filePath.getFileName()
                : folderName + "/" + filePath.getFileName();

        // Eliminar la llamada acl()
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build(), 
                RequestBody.fromBytes(Files.readAllBytes(filePath))
        );

        return fileKey;
    }

    /**
     * Subir un archivo desde un MultipartFile.
     */
    public String uploadFileToS3(String folderName, MultipartFile file, String fileName) throws IOException {
        validateKey(folderName);
        validateKey(fileName);

        String fileKey = folderName.endsWith("/") ? folderName + fileName : folderName + "/" + fileName;

        // Eliminar la llamada acl()
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return fileKey;
    }
    
    public String uploadFileToS3(String folderName, File file, String fileName) throws IOException {
        validateKey(folderName);
        validateKey(fileName);

        String fileKey = folderName.endsWith("/") ? folderName + fileName : folderName + "/" + fileName;

        // Eliminar la llamada acl()
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build(),
                RequestBody.fromFile(file)
        );

        return fileKey;
    }

    /**
     * Subir un archivo y hacerlo público.
     */
    public String uploadFileAndMakePublic(String folderName, MultipartFile file, String fileName) throws IOException {
        String fileKey = uploadFileToS3(folderName, file, fileName);
        makePublic(fileKey);
        return getPublicUrl(fileKey);
    }

    /**
     * Listar los archivos de un folder en S3.
     */
    public List<String> listFiles(String folderName) {
        validateKey(folderName);

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(folderName)
                .build();

        return s3Client.listObjectsV2(request).contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }



    /**
     * Hacer público un archivo en S3.
     */
    public void makePublic(String fileKey) {
        validateKey(fileKey);
        s3Client.putObjectAcl(PutObjectAclRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build());
    }

    /**
     * Obtener URL pública de un archivo en S3.
     */
    public String getPublicUrl(String fileKey) {
        validateKey(fileKey);
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileKey);
    }

    /**
     * Generar una URL firmada para acceso temporal.
     */
    public String generatePresignedUrl(String fileKey, int durationInMinutes) {
        validateKey(fileKey);
        try (S3Presigner presigner = S3Presigner.create()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(durationInMinutes))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return presigner.presignGetObject(presignRequest).url().toString();
        }
    }

    /**
     * Verificar si un archivo existe en S3.
     */
    public boolean fileExists(String fileKey) {
        validateKey(fileKey);
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            throw new RuntimeException("Error al verificar existencia del archivo: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    /**
     * Eliminar un archivo en S3.
     */
    public void deleteFile(String fileKey) {
        validateKey(fileKey);
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build());
        } catch (S3Exception e) {
            throw new RuntimeException("Error al eliminar el archivo de S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    /**
     * Descargar un archivo desde S3 y devolverlo como InputStream.
     */
    public InputStream downloadFileFromS3(String fileKey) throws IOException {
        validateKey(fileKey);
        // Descargar el archivo desde S3 y obtener un InputStream.
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build());

        // Retornar el InputStream
        return responseInputStream;
    }

    /**
     * Descargar el contenido de un archivo desde S3 como una cadena de texto.
     */
    public String downloadFileContentAsString(String fileKey) throws IOException {
        validateKey(fileKey);
        // Descargar el archivo desde S3 y obtener un InputStream.
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build());

        // Leer el InputStream y convertirlo a String
        try (InputStream inputStream = responseInputStream) {
            return new String(inputStream.readAllBytes());
        }
    }
}
