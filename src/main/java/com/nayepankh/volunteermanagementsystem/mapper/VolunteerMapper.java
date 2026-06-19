package com.nayepankh.volunteermanagementsystem.mapper;

import com.nayepankh.volunteermanagementsystem.volunteer.dto.request.VolunteerRequest;
import com.nayepankh.volunteermanagementsystem.volunteer.dto.response.VolunteerResponse;
import com.nayepankh.volunteermanagementsystem.volunteer.entity.Volunteer;
import com.nayepankh.volunteermanagementsystem.volunteer.enums.VolunteerStatus;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {

    public Volunteer toEntity(VolunteerRequest request) {
        return Volunteer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .skills(request.getSkills())
                .availability(request.getAvailability())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : VolunteerStatus.ACTIVE)
                .joinedDate(request.getJoinedDate())
                .build();
    }

    public VolunteerResponse toResponse(Volunteer volunteer) {
        return VolunteerResponse.builder()
                .id(volunteer.getId())
                .fullName(volunteer.getFullName())
                .email(volunteer.getEmail())
                .phoneNumber(volunteer.getPhoneNumber())
                .city(volunteer.getCity())
                .skills(volunteer.getSkills())
                .availability(volunteer.getAvailability())
                .status(volunteer.getStatus())
                .joinedDate(volunteer.getJoinedDate())
                .createdAt(volunteer.getCreatedAt())
                .updatedAt(volunteer.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(VolunteerRequest request, Volunteer volunteer) {
        volunteer.setFullName(request.getFullName());
        volunteer.setEmail(request.getEmail());
        volunteer.setPhoneNumber(request.getPhoneNumber());
        volunteer.setCity(request.getCity());
        volunteer.setSkills(request.getSkills());
        volunteer.setAvailability(request.getAvailability());
        if (request.getStatus() != null) {
            volunteer.setStatus(request.getStatus());
        }
        if (request.getJoinedDate() != null) {
            volunteer.setJoinedDate(request.getJoinedDate());
        }
    }

}


