package com.example.post.model;

import lombok.Data;

@Data
public class CreatePostRequest {
    private int userId;
    private String title;
    private String body;
}