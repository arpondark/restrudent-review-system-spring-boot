package com.shazan.restrudent.mapers;

import com.shazan.restrudent.domain.dto.TimeRangeDto;
import com.shazan.restrudent.domain.entity.TimeRange;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TimeRangeMapper {
    TimeRangeDto toDto(TimeRange timeRange);
    TimeRange toEntity(TimeRangeDto timeRangeDto);
}
