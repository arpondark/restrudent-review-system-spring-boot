package com.shazan.restrudent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private String streetNumber;
    private String streetName;
    private String unit;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
