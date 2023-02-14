package com.example.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse<T> {

    private int statusCode;
    private String message;
    private String requestedUri;
    private Map<String, String> parameters;
    private T data;
    private PageInfo pageInfo;

}