package com.shazan.restrudent.mapers;

import com.shazan.restrudent.domain.dto.AddressDto;
import com.shazan.restrudent.domain.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}
