package com.nayepankh.volunteermanagementsystem.auth.service.impl;


import com.nayepankh.volunteermanagementsystem.auth.dto.request.LoginRequest;
import com.nayepankh.volunteermanagementsystem.auth.dto.request.RegisterRequest;
import com.nayepankh.volunteermanagementsystem.auth.dto.response.AuthResponse;
import com.nayepankh.volunteermanagementsystem.auth.service.AuthService;
import com.nayepankh.volunteermanagementsystem.exception.EmailAlreadyExistsException;
import com.nayepankh.volunteermanagementsystem.exception.ResourceNotFoundException;
import com.nayepankh.volunteermanagementsystem.security.JwtTokenProvider;
import com.nayepankh.volunteermanagementsystem.user.entity.Role;
import com.nayepankh.volunteermanagementsystem.user.entity.User;
import com.nayepankh.volunteermanagementsystem.user.enums.RoleName;
import com.nayepankh.volunteermanagementsystem.user.repository.RoleRepository;
import com.nayepankh.volunteermanagementsystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new EmailAlreadyExistsException("Username already taken: " + request.getUsername());
        }

        // Determine role
        RoleName roleName;
        if ("admin".equalsIgnoreCase(request.getRole())) {
            roleName = RoleName.ROLE_ADMIN;
        } else {
            roleName = RoleName.ROLE_USER;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
        log.info("New user registered: {}", user.getUsername());

        // Auto-login after registration
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);

        return AuthResponse.builder()
                .accessToken(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()));

        String token = jwtTokenProvider.generateToken(authentication);

        User user = userRepository
                .findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User", "username/email", request.getUsernameOrEmail()));

        log.info("User logged in: {}", user.getUsername());

        return AuthResponse.builder()
                .accessToken(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}