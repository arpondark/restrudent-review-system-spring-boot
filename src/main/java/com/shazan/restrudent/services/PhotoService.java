package com.shazan.restrudent.services;

import com.shazan.restrudent.domain.entity.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PhotoService  {
    Photo upLoadPhoto(MultipartFile file);
    Optional<Resource> getPhotoAsResource(String id);
}
