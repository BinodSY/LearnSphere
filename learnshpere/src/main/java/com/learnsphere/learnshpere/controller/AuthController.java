package com.learnsphere.learnshpere.controller;
import java.security.Principal;
import java.time.Instant;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learnsphere.learnshpere.dto.AuthResponse;
import com.learnsphere.learnshpere.dto.AuthRequest;
import com.learnsphere.learnshpere.dto.RegisterRequest;
import com.learnsphere.learnshpere.model.User;
import com.learnsphere.learnshpere.dto.profile;
import com.learnsphere.learnshpere.repositories.UserRepository;
import com.learnsphere.learnshpere.security.JwtUtil;


// import com.learnsphere.learnshpere.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtil jwtUtil;
    // @Autowired
    // private  UserDetailsServiceImpl userDetailsService;

    // Get current user details
    @GetMapping("/me")
public ResponseEntity<?> getCurrentUser(Principal principal) {
    // principal.getName() comes from JWT (email)
    User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Return only safe fields
    Map<String, Object> userData = Map.of(
        "id", user.getId(),
        "name", user.getName(),
        "email", user.getEmail(),
        "role", user.getRole(),
        "bio", user.getBio()!= null ? user.getBio() : "",
        "skills", user.getSkills()!= null ? user.getSkills() :  Collections.emptyList()
    );

    return ResponseEntity.ok(userData);
}



   @PostMapping("/completeprofile")
public ResponseEntity<?> completeProfile(@RequestBody @Valid profile req, Principal principal) {
    // principal is extracted from JWT via Spring Security
    User existingUser = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Update only profile fields
    existingUser.setBio(req.getBio());
    existingUser.setSkills(req.getSkills());
    userRepository.save(existingUser);

    return ResponseEntity.ok(Map.of("message", "profile updated"));
}
    

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error","Email already in use"));
        }
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .createdAt(Instant.now())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message","registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest req) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails ud = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(ud.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(ud, user);

        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }
}
