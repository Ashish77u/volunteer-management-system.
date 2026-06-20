package com.nayepankh.volunteermanagementsystem.volunteer.controller;

import com.nayepankh.volunteermanagementsystem.util.AppConstants;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.request.VolunteerRequest;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.ApiResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.PagedResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.VolunteerResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
@Tag(name = "Volunteers", description = "Volunteer management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new volunteer (Admin only)")
    public ResponseEntity<ApiResponse<VolunteerResponse>> create(
            @Valid @RequestBody VolunteerRequest request) {
        VolunteerResponse response = volunteerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Volunteer created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all volunteers with pagination and sorting")
    public ResponseEntity<ApiResponse<PagedResponse<VolunteerResponse>>> getAll(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE)   int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY)     String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR)    String sortDir) {

        PagedResponse<VolunteerResponse> result =
                volunteerService.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success("Volunteers fetched successfully", result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get volunteer by ID")
    public ResponseEntity<ApiResponse<VolunteerResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Volunteer fetched", volunteerService.getById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update volunteer (Admin only)")
    public ResponseEntity<ApiResponse<VolunteerResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody VolunteerRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Volunteer updated", volunteerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete volunteer (Admin only)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        volunteerService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Volunteer deleted successfully", null));
    }

    // ─── Search Endpoints ──────────────────────────────────────────────────

    @GetMapping("/search")
    @Operation(summary = "Search volunteers by name, city, or skill")
    public ResponseEntity<ApiResponse<PagedResponse<VolunteerResponse>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String skill,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE)   int size) {

        PagedResponse<VolunteerResponse> result;

        if (name != null && !name.isBlank()) {
            result = volunteerService.searchByName(name, page, size);
        } else if (city != null && !city.isBlank()) {
            result = volunteerService.searchByCity(city, page, size);
        } else if (skill != null && !skill.isBlank()) {
            result = volunteerService.searchBySkill(skill, page, size);
        } else {
            result = volunteerService.getAll(page, size,
                    AppConstants.DEFAULT_SORT_BY, AppConstants.DEFAULT_SORT_DIR);
        }

        return ResponseEntity.ok(ApiResponse.success("Search results", result));
    }
}