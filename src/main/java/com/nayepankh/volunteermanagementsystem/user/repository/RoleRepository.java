package com.nayepankh.volunteermanagementsystem.user.repository;




import com.nayepankh.volunteermanagementsystem.user.entity.Role;
import com.nayepankh.volunteermanagementsystem.user.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}