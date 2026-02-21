package com.shazan.restrudent.controller;

import com.shazan.restrudent.domain.dto.CreateRestrurantRequest;
import com.shazan.restrudent.domain.dto.RestrurantDto;
import com.shazan.restrudent.services.RestrurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant", description = "Restaurant management APIs")
public class RestrurantController {
    private final RestrurantService restrurantService;

    @PostMapping
    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully",
                    content = @Content(schema = @Schema(implementation = RestrurantDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<RestrurantDto> createRestrurant(@Valid @RequestBody CreateRestrurantRequest request) {
        RestrurantDto created = restrurantService.createRestrurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Retrieves a restaurant by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found",
                    content = @Content(schema = @Schema(implementation = RestrurantDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<RestrurantDto> getRestrurantById(
            @Parameter(description = "Restaurant ID") @PathVariable String id) {
        return restrurantService.getRestrurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all restaurants", description = "Retrieves a paginated list of all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Page<RestrurantDto>> getAllRestrurants(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RestrurantDto> restaurants = restrurantService.getAllRestrurants(pageable);
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update restaurant", description = "Updates an existing restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully",
                    content = @Content(schema = @Schema(implementation = RestrurantDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<RestrurantDto> updateRestrurant(
            @Parameter(description = "Restaurant ID") @PathVariable String id,
            @Valid @RequestBody CreateRestrurantRequest request) {
        RestrurantDto updated = restrurantService.updateRestrurant(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant", description = "Deletes a restaurant by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> deleteRestrurant(
            @Parameter(description = "Restaurant ID") @PathVariable String id) {
        restrurantService.deleteRestrurant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search restaurants", description = "Searches restaurants by query string")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Page<RestrurantDto>> searchRestrurants(
            @Parameter(description = "Search query") @RequestParam String query,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RestrurantDto> results = restrurantService.searchRestrurants(query, pageable);
        return ResponseEntity.ok(results);
    }
}
