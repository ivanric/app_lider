package app.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

//import app.config.GoogleDriveConfig;

import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
/*
@Service
public class GoogleDriveService {
	String folderId = "1A8jdvE4B2jtNGQU60jTIjA82_PANTE99";
	
	private final Drive driveService;
	
    @Autowired
    public GoogleDriveService(Drive driveService) {
        this.driveService = driveService;
    }

    public String createFolder(String folderName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setParents(Collections.singletonList(folderId));
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = driveService.files().create(fileMetadata)
                .setFields("id")
                .execute();
        return file.getId();
    }
    
    
    
    
}*/
