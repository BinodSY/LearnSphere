package com.learnsphere.learnshpere.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

// import lombok.RequiredArgsConstructor;

import com.learnsphere.learnshpere.model.ForumPost;

import com.learnsphere.learnshpere.repositories.ForumRepository;

@Service
// @RequiredArgsConstructor
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;
    

    public ForumPost createPost(ForumPost post) {
        // post.setcreatedAt(Instant.now());
        return forumRepository.save(post);
    }

    public List<ForumPost> getAllPosts() {
        return forumRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Optional<ForumPost> getPostById(String id) {
        return forumRepository.findById(id);
    }

    public ForumPost addComment(String postId,ForumPost.Comment comment) {
        ForumPost post = forumRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setCreatedAt(Instant.now());
        post.getComments().add(comment);
        return forumRepository.save(post);
    }
}
