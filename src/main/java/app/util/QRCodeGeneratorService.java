package app.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

//import app.service.ArchivoService;
import app.service.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


@Service
public class QRCodeGeneratorService {

	

    private static final String CHARSET = "UTF-8";
    private static final String FILE_EXTENSION = "png";
    private static final int QR_WIDTH = 600;
    private static final int QR_HEIGHT = 600;

//    @Autowired
//    private ArchivoService archivoService; // Servicio para manejar archivos en Google Drive

    @Autowired
    private S3Service s3Service; // Servicio para manejar archivos en S3

    
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    private String ruta_logos = "";

    /**
     * Método para generar un código QR y guardarlo localmente y en Google Drive.
     *
     * @param message Mensaje que se codificará en el QR.
     * @param nombre Nombre del archivo del QR.
     */
    
    
    public void generateQRCode(String folderQR, String message, String nombre) {
        System.out.println("### Generating QRCode ###");
        try {
            String finalMessage = message;
            System.out.println("Final Input Message: " + finalMessage);
            String outputPath = prepareOutputFileName(nombre); // Ruta local

            // Procesa y guarda el QR localmente
            processQRCode(finalMessage, outputPath, CHARSET, QR_WIDTH, QR_HEIGHT);

            // Guarda el QR en S3 (en lugar de Google Drive)
            File qrFile = new File(outputPath);
            String fileKey = s3Service.uploadFileToS3(folderQR, qrFile, nombre + "." + FILE_EXTENSION); // Subir a S3

            // (Opcional) Si quieres obtener la URL pública del archivo QR en S3
            String fileUrl = s3Service.getPublicUrl(fileKey);
            System.out.println("QR uploaded to S3. Public URL: " + fileUrl);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    
    

    /**
     * Prepara el nombre del archivo de salida y la ruta de almacenamiento.
     *
     * @param nombre Nombre del archivo del QR.
     * @return Ruta completa donde se guardará el archivo QR.
     * @throws IOException Si hay un error al crear la carpeta o la ruta.
     */
    private String prepareOutputFileName(String nombre) throws IOException {
        String ruta = obtenerRutaArchivos(Constantes.nameFolderQrEvento);

        if (ruta == null || ruta.isEmpty()) {
            throw new IOException("No se pudo determinar la ruta de almacenamiento.");
        }

        Path rutaDirectorio = Paths.get(ruta).toAbsolutePath();
        if (!Files.exists(rutaDirectorio)) {
            System.out.println("*********CREANDO CARPETA DE QRS");
            Files.createDirectories(rutaDirectorio); // Crea el directorio si no existe
        }

        ruta_logos = Paths.get(ruta).toAbsolutePath().resolve(nombre).toString();
        System.out.println("RUTA DE QR FOLDER: " + ruta_logos);
        return ruta_logos + "." + FILE_EXTENSION;
    }
/*
    private void processQRCode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
        // Generar el código QR
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE,
                width,
                height
        );

//        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                int color = (matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Negro (QR) o Blanco (fondo)
//                qrImage.setRGB(x, y, color);
//            }
//        }
        
        // 🔹 Crear imagen con transparencia
        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = (matrix.get(x, y) ? 0xFF000000 : 0x00FFFFFF); // 🔹 Negro o Transparente
                qrImage.setRGB(x, y, color);
            }
        }

        // Cargar la imagen del logo
        Resource resource = new ClassPathResource("static/img/liderlogo.png");
        InputStream logoStream = resource.getInputStream();
        BufferedImage logo = ImageIO.read(logoStream);

        if (logo == null) {
            System.out.println("Error: No se pudo cargar el logo.");
            return;
        }

        // 🔹 Ajusta el tamaño del logo aquí:
        double logoScale = 0.2; // Proporción del tamaño del logo con respecto al QR (más pequeño)
        int logoWidth = (int) (width * logoScale*1.8);// Aumenta el ancho del logo
        int logoHeight = (int) (height * logoScale);

        BufferedImage resizedLogo = new BufferedImage(logoWidth, logoHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedLogo.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(logo, 0, 0, logoWidth, logoHeight, null);
        g2d.dispose();

        // 🔹 Ajusta el espacio blanco arriba y abajo aquí:
        int paddingTopBottom = (int) (logoHeight * 0.1); // 20% del tamaño del logo
        int newLogoHeight = logoHeight + (paddingTopBottom * 2);  // Nuevo alto con espacios

        BufferedImage logoWithBackground = new BufferedImage(logoWidth, newLogoHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBackground = logoWithBackground.createGraphics();
        gBackground.setColor(java.awt.Color.WHITE);
        gBackground.fillRect(0, 0, logoWidth, newLogoHeight);
        gBackground.drawImage(resizedLogo, 0, paddingTopBottom, null);
        gBackground.dispose();

        // Posición centrada del logo con fondo blanco
        int xPos = (width - logoWidth) / 2;
        int yPos = (height - newLogoHeight) / 2;
        Graphics2D g = qrImage.createGraphics();
        g.drawImage(logoWithBackground, xPos, yPos, null);
        g.dispose();

        // Guardar la imagen final con el logo
        File outputFile = new File(path);
        ImageIO.write(qrImage, "PNG", outputFile);
    }*/
//    private void processQRCode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
//        // 🔹 Escalar el logo para que no afecte demasiado el QR
//        double logoScale = 0.18; // El logo ocupará un 18% del QR
//        int logoWidth = (int) (width * logoScale);
//        int logoHeight = (int) (height * logoScale);
//
//        // 🔹 Generar código QR con márgenes adecuados
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.MARGIN, 2); // Margen extra para mejorar el escaneo
//
//        BitMatrix matrix = new MultiFormatWriter().encode(
//                new String(data.getBytes(charset), charset),
//                BarcodeFormat.QR_CODE,
//                width, height,
//                hints
//        );
//
//        // 🔹 Crear imagen QR en blanco
//        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = qrImage.createGraphics();
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, width, height);
//        g.setColor(Color.BLACK);
//
//        // 🔹 Dibujar el código QR normalmente
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (matrix.get(x, y)) {
//                    qrImage.setRGB(x, y, Color.BLACK.getRGB());
//                }
//            }
//        }
//
//        // 🔹 Cargar y redimensionar la imagen del logo
//        Resource resource = new ClassPathResource("static/img/liderlogo.png");
//        InputStream logoStream = resource.getInputStream();
//        BufferedImage logo = ImageIO.read(logoStream);
//
//        if (logo == null) {
//            System.out.println("Error: No se pudo cargar el logo.");
//            return;
//        }
//
//        BufferedImage resizedLogo = new BufferedImage(logoWidth, logoHeight, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = resizedLogo.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g2d.drawImage(logo, 0, 0, logoWidth, logoHeight, null);
//        g2d.dispose();
//
//        // 🔹 Crear un fondo blanco detrás del logo
//        int padding = 10; // Espacio blanco alrededor del logo para evitar que bloquee el QR
//        BufferedImage logoWithWhiteBackground = new BufferedImage(logoWidth + padding * 2, logoHeight + padding * 2, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D gWhite = logoWithWhiteBackground.createGraphics();
//        gWhite.setColor(Color.WHITE);
//        gWhite.fillRect(0, 0, logoWithWhiteBackground.getWidth(), logoWithWhiteBackground.getHeight());
//        gWhite.drawImage(resizedLogo, padding, padding, null);
//        gWhite.dispose();
//
//        // 🔹 Posicionar el logo en el centro del QR
//        int logoX = (width - logoWithWhiteBackground.getWidth()) / 2;
//        int logoY = (height - logoWithWhiteBackground.getHeight()) / 2;
//
//        g.drawImage(logoWithWhiteBackground, logoX, logoY, null);
//        g.dispose();
//
//        // 🔹 Guardar la imagen final
//        File outputFile = new File(path);
//        ImageIO.write(qrImage, "PNG", outputFile);
//    }

    private void processQRCode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
        // 🔹 Configuración para mejorar el escaneo del QR
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 2); // Ajusta los márgenes para mejor lectura

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE,
                width, height,
                hints
        );

        // 🔹 Crear imagen QR en blanco y negro sin logo
        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = qrImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);

        // 🔹 Dibujar el código QR
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    qrImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        g.dispose();

        // 🔹 Guardar la imagen final sin logo
        File outputFile = new File(path);
        ImageIO.write(qrImage, "PNG", outputFile);
    }


    public String obtenerRutaArchivos(String carpeta) {
        URIS uris = new URIS();
        String sistemaOperativo = uris.checkOS();
        System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
        String rutaCarpeta = "";

        try {
            if (sistemaOperativo.contains("Linux")) {
                darPermisosCarpeta("/home");
                rutaCarpeta = Paths.get("/home", carpeta).toString();
            } else if (sistemaOperativo.contains("Windows")) {
                rutaCarpeta = Paths.get("C:\\", carpeta).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
        }

        return rutaCarpeta;
    }

    /**
     * Asigna permisos a la carpeta en Linux.
     *
     * @param rutaBase Ruta base de la carpeta.
     * @throws IOException Si hay un error al asignar permisos.
     */
    private void darPermisosCarpeta(String rutaBase) throws IOException {
        Process p = Runtime.getRuntime().exec("chmod -R 755 " + rutaBase); // Permisos más seguros
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el contenido de un QR existente o lo crea si no existe.
     *
     * @param newContent Nuevo contenido del QR.
     * @param nombre Nombre del archivo del QR.
     */
    public void updateQRCodeContent(String FolderQr,String newContent, String nombre) {
        try {
            String rutaCatalogos = obtenerRutaArchivos(Constantes.nameFolderQrEvento);

            if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
                throw new IOException("No se pudo determinar la ruta de almacenamiento.");
            }

            Path rutaDirectorio = Paths.get(rutaCatalogos).toAbsolutePath();
            if (!Files.exists(rutaDirectorio)) {
                Files.createDirectories(rutaDirectorio);
            }

            ruta_logos = Paths.get(rutaCatalogos).toAbsolutePath().resolve(nombre + "." + FILE_EXTENSION).toString();

            if (!ruta_logos.isEmpty()) {
                File qrCodeFile = new File(ruta_logos);
                if (qrCodeFile.exists()) {
                    BufferedImage qrCodeImage = ImageIO.read(qrCodeFile);
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(newContent, BarcodeFormat.QR_CODE, qrCodeImage.getWidth(), qrCodeImage.getHeight());
                    try (FileOutputStream fos = new FileOutputStream(qrCodeFile)) {
                        MatrixToImageWriter.writeToStream(bitMatrix, FILE_EXTENSION, fos);
                    }
                    System.out.println("QR code updated successfully.");
                } else {
                    System.out.println("El archivo QR no existe, generando uno nuevo.");
                    generateQRCode(FolderQr,newContent, nombre);
                }
            } else {
                System.out.println("NO SE ENCONTRO EL QR: " + ruta_logos);
                generateQRCode(FolderQr,newContent, nombre);
            }

        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

	
//	 public String obtenerRutaCarpetaRecursos(String nombreCarpeta) {
//        try { 
//            Resource resource = resourceLoader.getResource("classpath:static/" + nombreCarpeta);
//            return resource.getFile().getAbsolutePath();
//        } catch (Exception e) {
//            System.out.println("Error al obtener la ruta de la carpeta de recursos: " + e.getMessage());
//            return null;
//        }
//    }
	 

       /*
        try {
            String path = prepareOutputFileName(nombre);
            System.out.println("PATH MOFOOOOO:"+path); 
            if (Files.exists(Paths.get(path))) {
                // Actualizar el contenido del QR sin regenerarlo
                System.out.println("Actualizando contenido del código QR en: " + path);
                Files.writeString(Paths.get(path), newContent);
            } else {
                System.out.println("El código QR no existe en: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    
    

}
