package com.nayepankh.volunteermanagementsystem.volunteer.dto.response;

import com.nayepankh.volunteermanagementsystem.volunteer.dto.request.VolunteerRequest;
import com.nayepankh.volunteermanagementsystem.volunteer.enums.VolunteerStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerResponse {
    private Long            id;
    private String          fullName;
    private String          email;
    private String          phoneNumber;
    private String          city;
    private String          skills;
    private String          availability;
    private VolunteerStatus status;
    private LocalDate       joinedDate;
    private LocalDateTime   createdAt;
    private LocalDateTime   updatedAt;
}