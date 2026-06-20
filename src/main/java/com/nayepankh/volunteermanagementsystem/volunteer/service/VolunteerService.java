package com.nayepankh.volunteermanagementsystem.volunteer.service;

import com.nayepankh.volunteermanagementsystem.volunteer.dto.request.VolunteerRequest;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.PagedResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.VolunteerResponse;

public interface VolunteerService {

    VolunteerResponse create(VolunteerRequest request);
    PagedResponse<VolunteerResponse> getAll(int page, int size, String sortBy, String sortDir);
    VolunteerResponse getById(Long id);
    VolunteerResponse update(Long id, VolunteerRequest request);
    void delete(Long id);
    PagedResponse<VolunteerResponse> searchByName(String name, int page, int size);
    PagedResponse<VolunteerResponse> searchByCity(String city, int page, int size);
    PagedResponse<VolunteerResponse> searchBySkill(String skill, int page, int size);
}