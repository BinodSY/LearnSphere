package com.learnsphere.learnshpere.controller;
import java.util.List;
// import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learnsphere.learnshpere.model.ForumPost;
import com.learnsphere.learnshpere.model.ForumPost.Comment;
import com.learnsphere.learnshpere.service.ForumService;
// import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
@RestController
@RequestMapping("/api/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    // ✅ Create a new post

    @GetMapping("/health")
    public String healthCheck() {
        return "Forum Service is up and running!";
    }
    @PostMapping
    public ResponseEntity<ForumPost> createPost(@RequestBody @Validated ForumPost post) {
        return ResponseEntity.ok(forumService.createPost(post));
    }

    // ✅ Get all posts
    @GetMapping
    public ResponseEntity<List<ForumPost>> getAllPosts() {
        return ResponseEntity.ok(forumService.getAllPosts());
    }

    // ✅ Get single post (with comments)
    @GetMapping("/{id}")
    public ResponseEntity<ForumPost> getPost(@PathVariable String id) {
        return forumService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Add comment to post
    @PostMapping("/{id}/comment")
    public ResponseEntity<ForumPost> addComment(@PathVariable String id,
                                                @RequestBody @Validated Comment comment) {
        return ResponseEntity.ok(forumService.addComment(id, comment));
    }
}