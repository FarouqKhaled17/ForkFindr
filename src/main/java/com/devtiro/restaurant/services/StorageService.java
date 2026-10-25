package com.devtiro.restaurant.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

public interface StorageService {
//    MultipartFile is a Spring interface that represents a file uploaded by a client through an HTTP request.
    String store (MultipartFile file, String fileName);
//    Loads a file as a resource.
    Optional<Resource> loadAsResource (String id);
}