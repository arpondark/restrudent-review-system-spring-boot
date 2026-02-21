package com.shazan.restrudent.controller;

import com.shazan.restrudent.domain.dto.PhotoDto;
import com.shazan.restrudent.mapers.PhotoMapper;
import com.shazan.restrudent.services.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
@Tag(name = "Photo", description = "Photo upload and retrieval APIs")
public class PhotoController {
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping
    @Operation(summary = "Upload a photo", description = "Uploads a photo file and returns photo metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photo uploaded successfully",
                    content = @Content(schema = @Schema(implementation = PhotoDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public PhotoDto uploadPhoto(
            @Parameter(description = "Photo file to upload", required = true)
            @RequestParam("file") MultipartFile file) {
        return photoMapper.toDto(photoService.upLoadPhoto(file));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get photo by ID", description = "Retrieves a photo file by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photo retrieved successfully",
                    content = @Content(mediaType = "image/jpeg")),
            @ApiResponse(responseCode = "404", description = "Photo not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Resource> getPhoto(
            @Parameter(description = "Photo ID") @PathVariable String id) {
        return photoService.getPhotoAsResource(id)
                .map(resource -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + id + "\"")
                        .body(resource))
                .orElse(ResponseEntity.notFound().build());
    }
}
