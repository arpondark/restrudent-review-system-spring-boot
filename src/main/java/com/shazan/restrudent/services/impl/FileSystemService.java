package com.shazan.restrudent.services.impl;

import com.shazan.restrudent.expections.StorageException;
import com.shazan.restrudent.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Component
public class FileSystemService implements StorageService {
    private static final Logger log = LoggerFactory.getLogger(FileSystemService.class);

    @Value("${app.storage.location:uploads}")
    private String storagePath;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        this.rootLocation = Path.of(storagePath);
        // Create the directory if it doesn't exist
        try {
            java.nio.file.Files.createDirectories(rootLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }


    @Override
    public String store(MultipartFile file, String filename) throws IOException {
        if(file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String finalFileName = filename + "." + extension;

        Path destinationFile = rootLocation
                .resolve(Paths.get(finalFileName))
                .normalize()
                .toAbsolutePath();
        if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside specified directory");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return finalFileName;
    }

    @Override
    public Optional<Resource> load(String id) {
        try {
            Path file = rootLocation.resolve(id);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return Optional.of(resource);
            } else {
                return Optional.empty();
            }
        } catch (MalformedURLException e) {
            log.warn("Could not read file: %s".formatted(id), e);
            return Optional.empty();
        }
    }
}
