package com.shazan.restrudent.mapers;

import com.shazan.restrudent.domain.dto.PhotoDto;
import com.shazan.restrudent.domain.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {
    PhotoDto toDto(Photo photo);

}
