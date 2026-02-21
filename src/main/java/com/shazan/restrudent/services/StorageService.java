package com.shazan.restrudent.services;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface StorageService {
    String store(MultipartFile file, String filename) throws IOException;
    Optional<Resource> load(String id);
}
