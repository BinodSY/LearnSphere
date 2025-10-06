package com.learnsphere.learnshpere.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String bio;
    private List<String> skills;
    
    @Builder.Default
    private  Instant createdAt = Instant.now();
}
