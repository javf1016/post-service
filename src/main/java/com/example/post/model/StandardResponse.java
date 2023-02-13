package com.example.post.model;

import lombok.Data;

import java.util.Map;

@Data
public class StandardResponse<T> {

    private int statusCode;
    private String message;
    private String requestedUri;
    private Map<String, String> parameters;
    private T data;
    private PageInfo pageInfo;

    public StandardResponse(int statusCode, String message, String requestedUri, Map<String, String> parameters, T data, PageInfo pageInfo) {
        this.statusCode = statusCode;
        this.message = message;
        this.requestedUri = requestedUri;
        this.parameters = parameters;
        this.data = data;
        this.pageInfo = pageInfo;
    }
}