package com.shazan.restrudent.services.impl;

import com.shazan.restrudent.domain.entity.Photo;
import com.shazan.restrudent.services.PhotoService;
import com.shazan.restrudent.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final StorageService storageService;

    @Override
    public Photo upLoadPhoto(MultipartFile file) {
        try {
            String filename = UUID.randomUUID().toString();
            String storedFilename = storageService.store(file, filename);

            return Photo.builder()
                    .url(storedFilename)
                    .uploadDate(LocalDateTime.now())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload photo", e);
        }
    }

    @Override
    public Optional<Resource> getPhotoAsResource(String id) {
        return storageService.load(id);
    }
}



