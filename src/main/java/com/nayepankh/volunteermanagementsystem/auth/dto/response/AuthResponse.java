package com.nayepankh.volunteermanagementsystem.auth.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String tokenType = "Bearer";
    private String accessToken;
    private String username;
    private String email;
}