package com.learnsphere.learnshpere.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learnsphere.learnshpere.model.Content;
import com.learnsphere.learnshpere.repositories.ContentRepository;

// import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public Content createContent(Content content) {
        content.setCreatedAt(Instant.now());
        content.setLikes(0);
        content.setViews(0);
        return contentRepository.save(content);
    }

    public List<Content> getAllContent() {
        return contentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Optional<Content> getContentById(String id) {
        return contentRepository.findById(id)
                .map(c -> {
                    c.setViews(c.getViews() + 1);
                    return contentRepository.save(c);
                });
    }

    public Content likeContent(String contentId, String userId) {
        Content content = contentRepository.findById(contentId)
            .orElseThrow(() -> new RuntimeException("Content not found"));

    if (content.getLikedBy().contains(userId)) {
        // User already liked → remove like
        content.getLikedBy().remove(userId);
    } else {
        // First time like → add
        content.getLikedBy().add(userId);
    }
    // Update total likes count
    content.setLikes(content.getLikedBy().size());
    return contentRepository.save(content);
    }
}
