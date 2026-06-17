package com.nayepankh.volunteermanagementsystem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {

    @NotBlank(message = "Username and email is required")
    private String usernameOrEmail;

    @NotBlank(message = "password is required")
    private String password;



}
