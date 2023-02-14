package com.example.post.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}