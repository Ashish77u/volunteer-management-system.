package com.nayepankh.volunteermanagementsystem.volunteer.service.impl;

import com.nayepankh.volunteermanagementsystem.exception.EmailAlreadyExistsException;
import com.nayepankh.volunteermanagementsystem.exception.ResourceNotFoundException;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.request.VolunteerRequest;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.PagedResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.VolunteerResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.entity.Volunteer;
import com.nayepankh.volunteermanagementsystem.volunteer.mapper.VolunteerMapper;
import com.nayepankh.volunteermanagementsystem.volunteer.repository.VolunteerRepository;
import com.nayepankh.volunteermanagementsystem.volunteer.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;

    @Override
    @Transactional
    public VolunteerResponse create(VolunteerRequest request) {
        if (volunteerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        Volunteer saved = volunteerRepository.save(volunteerMapper.toEntity(request));
        log.info("Volunteer created with id: {}", saved.getId());
        return volunteerMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<VolunteerResponse> getAll(
            int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Volunteer> volunteers = volunteerRepository.findAll(pageable);
        return buildPagedResponse(volunteers);
    }

    @Override
    @Transactional(readOnly = true)
    public VolunteerResponse getById(Long id) {
        Volunteer volunteer = findOrThrow(id);
        return volunteerMapper.toResponse(volunteer);
    }

    @Override
    @Transactional
    public VolunteerResponse update(Long id, VolunteerRequest request) {
        Volunteer volunteer = findOrThrow(id);

        // If email changed, check it's not taken by another volunteer
        if (!volunteer.getEmail().equalsIgnoreCase(request.getEmail())
                && volunteerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        volunteerMapper.updateEntityFromRequest(request, volunteer);
        Volunteer updated = volunteerRepository.save(volunteer);
        log.info("Volunteer updated: {}", updated.getId());
        return volunteerMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Volunteer volunteer = findOrThrow(id);
        volunteerRepository.delete(volunteer);
        log.info("Volunteer deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<VolunteerResponse> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Volunteer> result = volunteerRepository
                .findByFullNameContainingIgnoreCase(name, pageable);
        return buildPagedResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<VolunteerResponse> searchByCity(String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Volunteer> result = volunteerRepository
                .findByCityContainingIgnoreCase(city, pageable);
        return buildPagedResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<VolunteerResponse> searchBySkill(String skill, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Volunteer> result = volunteerRepository
                .findBySkillContaining(skill, pageable);
        return buildPagedResponse(result);
    }

    // ─── Private Helpers ──────────────────────────────────────────────────

    private Volunteer findOrThrow(Long id) {
        return volunteerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer", "id", id));
    }

    private PagedResponse<VolunteerResponse> buildPagedResponse(Page<Volunteer> page) {
        List<VolunteerResponse> content = page.getContent()
                .stream()
                .map(volunteerMapper::toResponse)
                .toList();

        return PagedResponse.<VolunteerResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}