package com.learnsphere.learnshpere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.learnsphere.learnshpere.model.Role;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String id;
    private String name;
    private String email;
    private Role role;
}
