package com.shazan.restrudent.domain.dto;

import com.shazan.restrudent.domain.dto.PhotoDto;
import com.shazan.restrudent.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private String id;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PhotoDto> photos = new ArrayList<>();
    private UserDto writtenBy;
}
