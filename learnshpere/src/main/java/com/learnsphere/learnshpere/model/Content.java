package com.learnsphere.learnshpere.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Content {
    @Id private String id;
  private String title;
  private String authorId;
  private String authorName;
  private String type; // "article","video"
  private String body; // text or URL
  private List<String> tags;
  private int likes;
  private int views;
  private Set<String> likedBy = new HashSet<>();
  private Instant createdAt = Instant.now();
}
