package com.devtiro.restaurant.services;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

public interface StorageService {
    String store (MultipartFile file, String fileName);
    Optional<Resource> loadAsResource (String id);
}
