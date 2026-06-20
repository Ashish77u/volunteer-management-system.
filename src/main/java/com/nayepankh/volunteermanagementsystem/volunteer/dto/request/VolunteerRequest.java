package com.nayepankh.volunteermanagementsystem.volunteer.dto.request;

import com.nayepankh.volunteermanagementsystem.volunteer.enums.VolunteerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @Size(max = 50)
    private String city;

    private String skills;

    @Size(max = 100)
    private String availability;

    private VolunteerStatus status;

    private LocalDate joinedDate;
}