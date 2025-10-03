package com.learnsphere.learnshpere.dto;
import com.learnsphere.learnshpere.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
