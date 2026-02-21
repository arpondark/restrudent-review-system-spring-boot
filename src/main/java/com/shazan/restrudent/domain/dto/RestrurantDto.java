package com.shazan.restrudent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestrurantDto {
    private String id;
    private String name;
    private String cuisineType;
    private String contactInformation;
    private Float averageRating;
    private Double latitude;
    private Double longitude;
    private AddressDto address;
    private OperatingHourDto operatingHours;
    private List<PhotoDto> photos = new ArrayList<>();
    private UserDto createdBy;
}
