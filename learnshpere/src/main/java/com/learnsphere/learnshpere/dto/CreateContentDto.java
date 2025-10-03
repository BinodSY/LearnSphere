package com.learnsphere.learnshpere.dto;
import lombok.Data;
import java.util.List;

@Data
public class CreateContentDto {
    private String title;
    private String type;   // "article", "video", etc.
    private String body;   // tutorial text or video URL
    private List<String> tags;
}

