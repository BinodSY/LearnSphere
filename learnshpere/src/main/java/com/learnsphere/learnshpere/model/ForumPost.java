package com.learnsphere.learnshpere.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.Builder;
 
@Document(collection  = "posts")
@Data 
public class ForumPost {
  @Id private String id;
  private String title;
  private String content;
  private String authorId;
  private String authorName;
  private List<String> tags;
  private List<Comment> comments = new ArrayList<>();
  private Instant createdAt = Instant.now();

  @Data
  public static class Comment {
    private String id = UUID.randomUUID().toString();
    private String authorId;
    private String authorName;
    private String text;
    private Instant createdAt = Instant.now();
  }
}
  
