package com.devtiro.restaurant.services.impl;

import com.devtiro.restaurant.services.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {
    @Override
    public String store(MultipartFile file, String fileName) {
        return "";
    }

    @Override
    public Optional<Resource> loadAsResource(String id) {
        return Optional.empty();
    }
}
