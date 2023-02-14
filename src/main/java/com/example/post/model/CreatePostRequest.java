package com.example.post.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private int userId;
    private String title;
    private String body;
}