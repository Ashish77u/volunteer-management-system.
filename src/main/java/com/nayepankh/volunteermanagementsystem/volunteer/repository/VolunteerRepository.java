package com.nayepankh.volunteermanagementsystem.volunteer.repository;


import com.nayepankh.volunteermanagementsystem.volunteer.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    boolean existsByEmail(String email);

    Optional<Volunteer> findByEmail(String email);

    // Search by name (case-insensitive)
    Page<Volunteer> findByFullNameContainingIgnoreCase(String name, Pageable pageable);

    // Search by city (case-insensitive)
    Page<Volunteer> findByCityContainingIgnoreCase(String city, Pageable pageable);

    // Search by skill using LIKE on TEXT column
    @Query("SELECT v FROM Volunteer v WHERE LOWER(v.skills) LIKE LOWER(CONCAT('%', :skill, '%'))")
    Page<Volunteer> findBySkillContaining(@Param("skill") String skill, Pageable pageable);
}