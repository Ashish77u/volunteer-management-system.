package com.nayepankh.volunteermanagementsystem.volunteer.entity;


import com.nayepankh.volunteermanagementsystem.volunteer.enums.VolunteerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "volunteers", indexes = {
        @Index(name = "idx_vol_city",   columnList = "city"),
        @Index(name = "idx_vol_status", columnList = "status"),
        @Index(name = "idx_vol_name",   columnList = "full_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 50)
    private String city;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(length = 100)
    private String availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerStatus status = VolunteerStatus.ACTIVE;

    private LocalDate joinedDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}