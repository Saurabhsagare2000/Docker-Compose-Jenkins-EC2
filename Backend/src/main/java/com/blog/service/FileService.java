package com.blog.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // Upload Image
    String uploadImage(
            String path,
            MultipartFile file
    ) throws IOException;

    // Get Image Resource
    InputStream getResource(
            String path,
            String fileName
    ) throws FileNotFoundException;
}