package com.nayepankh.volunteermanagementsystem.user.repository;

import com.nayepankh.volunteermanagementsystem.user.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}