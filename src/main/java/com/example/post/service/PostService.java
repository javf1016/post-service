package com.example.post.service;

import com.example.post.model.CreatePostRequest;
import com.example.post.model.Post;
import com.example.post.model.StandardResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

/***
 *  TODO:
 * **/
@Service
public interface PostService {

    /**
     *
     * Create a post with a given user id in the request
     *
     * **/
    StandardResponse createPost(CreatePostRequest requestPost, HttpServletRequest request);


    /***
     *
     * Get a post's information and register a visit to it
     *
     * **/
    StandardResponse<Post> visit(HttpServletRequest request);

    /**
     *
     * Get all posts made by a given user (paginated)
     *
     * **/
    StandardResponse<Collection<CreatePostRequest>> getAllByUser(int userId, int page, int size, HttpServletRequest request);

    StandardResponse<Post>  findPostById(Integer Id);

}
