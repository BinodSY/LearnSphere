package com.learnsphere.learnshpere.dto;

import java.util.List;

import lombok.Data;

@Data 
public class ForumPostRequestDto {
    private String title;
    private String content;
    private List<String> tags;
}
