package com.blog.ServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.service.FileService;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(
            String path,
            MultipartFile file)
            throws IOException {

        // Original file name
        String name =
                file.getOriginalFilename();

        // Random unique ID
        String randomId =
                UUID.randomUUID().toString();

        // New file name
        String fileName =
                randomId.concat(
                        name.substring(
                                name.lastIndexOf(".")
                        )
                );

        // Full path
        String filePath =
                path + File.separator + fileName;

        // Create folder if not exists
        File folder =
                new File(path);

        if (!folder.exists()) {
            folder.mkdir();
        }

        // Copy file
        Files.copy(
                file.getInputStream(),
                Paths.get(filePath)
        );

        return fileName;
    }

    @Override
    public InputStream getResource(
            String path,
            String fileName)
            throws FileNotFoundException {

        String fullPath =
                path + File.separator + fileName;

        InputStream is =
                new FileInputStream(fullPath);

        return is;
    }
}