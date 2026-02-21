package com.shazan.restrudent.mapers;

import com.shazan.restrudent.domain.dto.RestrurantDto;
import com.shazan.restrudent.domain.entity.Restrurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AddressMapper.class, OperatingHourMapper.class, PhotoMapper.class, UserMapper.class})
public interface RestrurantMapper {

    @Mapping(target = "latitude", expression = "java(restrurant.getGeoLocation() != null ? restrurant.getGeoLocation().getLat() : null)")
    @Mapping(target = "longitude", expression = "java(restrurant.getGeoLocation() != null ? restrurant.getGeoLocation().getLon() : null)")
    RestrurantDto toDto(Restrurant restrurant);

    @Mapping(target = "geoLocation", expression = "java(createGeoPoint(dto.getLatitude(), dto.getLongitude()))")
    Restrurant toEntity(RestrurantDto dto);

    default GeoPoint createGeoPoint(Double latitude, Double longitude) {
        if (latitude != null && longitude != null) {
            return new GeoPoint(latitude, longitude);
        }
        return null;
    }
}
