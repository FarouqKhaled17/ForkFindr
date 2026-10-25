package com.devtiro.restaurant.services.impl;

import com.devtiro.restaurant.exceptions.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSystemStorageServiceTest {
    @TempDir
    Path tempDir;

    private FileSystemStorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new FileSystemStorageService();
        ReflectionTestUtils.setField(storageService, "storageLocation", tempDir.toString());
        storageService.init();
    }

    @Test
    void initCreatesStorageDirectory() {
        assertTrue(Files.isDirectory(tempDir));
    }

    @Test
    void storeWritesFileAndReturnsFileName() throws IOException {
        byte[] contents = "image contents".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.png", "image/png", contents);

        String storedFileName = storageService.store(file, "restaurant-photo");

        assertEquals("restaurant-photo.png", storedFileName);
        assertArrayEquals(contents, Files.readAllBytes(tempDir.resolve(storedFileName)));
    }

    @Test
    void storeRejectsEmptyFile() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.png", "image/png", new byte[0]);

        assertThrows(StorageException.class, () -> storageService.store(file, "photo"));
    }

    @Test
    void storeRejectsPathTraversal() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.png", "image/png", "image".getBytes());

        assertThrows(StorageException.class, () -> storageService.store(file, "../outside"));
        assertFalse(Files.exists(tempDir.getParent().resolve("outside.png")));
    }

    @Test
    void loadAsResourceReturnsStoredFile() throws IOException {
        byte[] contents = "image contents".getBytes();
        Files.write(tempDir.resolve("photo.png"), contents);

        Optional<Resource> result = storageService.loadAsResource("photo.png");

        assertTrue(result.isPresent());
        assertTrue(result.get().isReadable());
        try (var inputStream = result.get().getInputStream()) {
            assertArrayEquals(contents, inputStream.readAllBytes());
        }
    }

    @Test
    void loadAsResourceReturnsEmptyWhenFileDoesNotExist() {
        assertTrue(storageService.loadAsResource("missing.png").isEmpty());
    }
}
