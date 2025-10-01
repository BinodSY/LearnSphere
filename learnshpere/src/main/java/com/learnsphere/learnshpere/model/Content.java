package com.learnsphere.learnshpere.model;

import java.time.Instant;
import java.util.List;

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
  private Instant createdAt = Instant.now();
}
