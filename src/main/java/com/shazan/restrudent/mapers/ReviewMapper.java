package com.shazan.restrudent.mapers;

import com.shazan.restrudent.domain.dto.ReviewDto;
import com.shazan.restrudent.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PhotoMapper.class, UserMapper.class})
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review toEntity(ReviewDto reviewDto);
}
