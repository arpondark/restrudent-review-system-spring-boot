package com.shazan.restrudent.mapers;

import com.shazan.restrudent.domain.dto.OperatingHourDto;
import com.shazan.restrudent.domain.entity.OperatingHour;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {TimeRangeMapper.class})
public interface OperatingHourMapper {
    OperatingHourDto toDto(OperatingHour operatingHour);
    OperatingHour toEntity(OperatingHourDto operatingHourDto);
}
