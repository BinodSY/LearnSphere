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

    public Content likeContent(String id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        content.setLikes(content.getLikes() + 1);
        return contentRepository.save(content);
    }
}
