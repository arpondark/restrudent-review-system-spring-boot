package com.shazan.restrudent.services;

import com.shazan.restrudent.domain.dto.CreateRestrurantRequest;
import com.shazan.restrudent.domain.dto.RestrurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RestrurantService {
    RestrurantDto createRestrurant(CreateRestrurantRequest request);
    Optional<RestrurantDto> getRestrurantById(String id);
    Page<RestrurantDto> getAllRestrurants(Pageable pageable);
    RestrurantDto updateRestrurant(String id, CreateRestrurantRequest request);
    void deleteRestrurant(String id);
    Page<RestrurantDto> searchRestrurants(String query, Pageable pageable);
}
