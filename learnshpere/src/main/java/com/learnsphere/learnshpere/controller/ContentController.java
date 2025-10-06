package com.learnsphere.learnshpere.controller;
 
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import jakarta.*;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learnsphere.learnshpere.model.Content;
import com.learnsphere.learnshpere.model.User;
import com.learnsphere.learnshpere.repositories.UserRepository;
import com.learnsphere.learnshpere.service.ContentService;
// import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/content")
// @RequiredArgsConstructor
public class ContentController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserRepository userRepository;


    // ✅ Mentor creates content
    @PostMapping
    @PreAuthorize("hasAuthority('MENTOR')")
    public ResponseEntity<Content> createContent(@RequestBody @Validated Content content) {
        return ResponseEntity.ok(contentService.createContent(content));
    }

    // ✅ List all content
    @GetMapping
    public ResponseEntity<List<Content>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    // ✅ View one content (increments view count)
    @GetMapping("/{id}")
    public ResponseEntity<Content> getContent(@PathVariable String id) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Like content increse when hitting to this end point
    @PostMapping("/{id}/like")
    public ResponseEntity<Content> likeContent(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Content updatedContent = contentService.likeContent(id, user.getId());
    return ResponseEntity.ok(updatedContent);
    }
}
