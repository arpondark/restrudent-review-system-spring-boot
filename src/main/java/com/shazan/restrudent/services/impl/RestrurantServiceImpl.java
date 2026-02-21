package com.shazan.restrudent.services.impl;

import com.shazan.restrudent.domain.dto.CreateRestrurantRequest;
import com.shazan.restrudent.domain.dto.RestrurantDto;
import com.shazan.restrudent.domain.entity.Restrurant;
import com.shazan.restrudent.domain.entity.User;
import com.shazan.restrudent.mapers.RestrurantMapper;
import com.shazan.restrudent.repo.RestrurantRepo;
import com.shazan.restrudent.services.RestrurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestrurantServiceImpl implements RestrurantService {
    private final RestrurantRepo restrurantRepo;
    private final RestrurantMapper restrurantMapper;

    @Override
    public RestrurantDto createRestrurant(CreateRestrurantRequest request) {
        Restrurant restrurant = Restrurant.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInformation(request.getContactInformation())
                .averageRating(0.0f)
                .geoLocation(new GeoPoint(request.getLatitude(), request.getLongitude()))
                .address(request.getAddress() != null ?
                        com.shazan.restrudent.domain.entity.Address.builder()
                            .streetNumber(request.getAddress().getStreetNumber())
                            .streetName(request.getAddress().getStreetName())
                            .unit(request.getAddress().getUnit())
                            .city(request.getAddress().getCity())
                            .state(request.getAddress().getState())
                            .postalCode(request.getAddress().getPostalCode())
                            .country(request.getAddress().getCountry())
                            .build() : null)
                .operatingHours(request.getOperatingHours() != null ?
                        mapOperatingHours(request.getOperatingHours()) : null)
                .photos(request.getPhotos() != null ?
                        request.getPhotos().stream()
                            .map(photoDto -> com.shazan.restrudent.domain.entity.Photo.builder()
                                .url(photoDto.getUrl())
                                .uploadDate(photoDto.getUploadDate())
                                .build())
                            .toList() : null)
                .createdBy(getCurrentUser())
                .build();

        Restrurant saved = restrurantRepo.save(restrurant);
        log.info("Restaurant created with id: {}", saved.getId());
        return restrurantMapper.toDto(saved);
    }

    @Override
    public Optional<RestrurantDto> getRestrurantById(String id) {
        return restrurantRepo.findById(id)
                .map(restrurantMapper::toDto);
    }

    @Override
    public Page<RestrurantDto> getAllRestrurants(Pageable pageable) {
        return restrurantRepo.findAll(pageable)
                .map(restrurantMapper::toDto);
    }

    @Override
    public RestrurantDto updateRestrurant(String id, CreateRestrurantRequest request) {
        Restrurant existing = restrurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));

        existing.setName(request.getName());
        existing.setCuisineType(request.getCuisineType());
        existing.setContactInformation(request.getContactInformation());
        existing.setGeoLocation(new GeoPoint(request.getLatitude(), request.getLongitude()));

        if (request.getAddress() != null) {
            existing.setAddress(com.shazan.restrudent.domain.entity.Address.builder()
                    .streetNumber(request.getAddress().getStreetNumber())
                    .streetName(request.getAddress().getStreetName())
                    .unit(request.getAddress().getUnit())
                    .city(request.getAddress().getCity())
                    .state(request.getAddress().getState())
                    .postalCode(request.getAddress().getPostalCode())
                    .country(request.getAddress().getCountry())
                    .build());
        }

        if (request.getOperatingHours() != null) {
            existing.setOperatingHours(mapOperatingHours(request.getOperatingHours()));
        }

        Restrurant updated = restrurantRepo.save(existing);
        log.info("Restaurant updated with id: {}", updated.getId());
        return restrurantMapper.toDto(updated);
    }

    @Override
    public void deleteRestrurant(String id) {
        restrurantRepo.deleteById(id);
        log.info("Restaurant deleted with id: {}", id);
    }

    @Override
    public Page<RestrurantDto> searchRestrurants(String query, Pageable pageable) {
        // This is a basic implementation - you can enhance with more sophisticated search
        return restrurantRepo.findAll(pageable)
                .map(restrurantMapper::toDto);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return User.builder()
                    .id(jwt.getSubject())
                    .username(jwt.getClaim("preferred_username"))
                    .givename(jwt.getClaim("given_name"))
                    .familyName(jwt.getClaim("family_name"))
                    .build();
        }
        return null;
    }

    private com.shazan.restrudent.domain.entity.OperatingHour mapOperatingHours(
            com.shazan.restrudent.domain.dto.OperatingHourDto dto) {
        return com.shazan.restrudent.domain.entity.OperatingHour.builder()
                .monday(dto.getMonday() != null ? mapTimeRange(dto.getMonday()) : null)
                .tuesday(dto.getTuesday() != null ? mapTimeRange(dto.getTuesday()) : null)
                .wednesday(dto.getWednesday() != null ? mapTimeRange(dto.getWednesday()) : null)
                .thursday(dto.getThursday() != null ? mapTimeRange(dto.getThursday()) : null)
                .friday(dto.getFriday() != null ? mapTimeRange(dto.getFriday()) : null)
                .saturday(dto.getSaturday() != null ? mapTimeRange(dto.getSaturday()) : null)
                .sunday(dto.getSunday() != null ? mapTimeRange(dto.getSunday()) : null)
                .build();
    }

    private com.shazan.restrudent.domain.entity.TimeRange mapTimeRange(
            com.shazan.restrudent.domain.dto.TimeRangeDto dto) {
        return com.shazan.restrudent.domain.entity.TimeRange.builder()
                .opentTime(dto.getOpentTime())
                .closeTime(dto.getCloseTime())
                .build();
    }
}
