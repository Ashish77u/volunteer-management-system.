package com.nayepankh.volunteermanagementsystem.volunteer.entity;


import com.nayepankh.volunteermanagementsystem.volunteer.enums.VolunteeerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "volunteers")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, length = 50, unique = true)
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
    private VolunteeerStatus status =  VolunteeerStatus.ACTIVE;

    private LocalDateTime jointedDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;






}
