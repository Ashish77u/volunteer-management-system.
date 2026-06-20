package com.nayepankh.volunteermanagementsystem.auth.service;

import com.nayepankh.volunteermanagementsystem.auth.dto.request.LoginRequest;
import com.nayepankh.volunteermanagementsystem.auth.dto.request.RegisterRequest;
import com.nayepankh.volunteermanagementsystem.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}