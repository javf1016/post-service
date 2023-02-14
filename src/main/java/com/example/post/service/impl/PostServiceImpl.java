package com.example.post.service.impl;

import com.example.post.model.*;
import com.example.post.repository.PostRepository;
import com.example.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public StandardResponse<Post> createPost(CreatePostRequest requestPost, HttpServletRequest request) {
        Post post = new Post();
        //http://localhost:9001/user/551
        StandardResponse<User> forObject = restTemplate.getForObject("http://localhost:9001/user/"+requestPost.getUserId(), StandardResponse.class);
        logger.info("{}",forObject);
        post.setUserId(forObject.getData().getId());
        post.setTitle(requestPost.getTitle());
        post.setBody(requestPost.getBody());
        post.setViews(0);
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Timestamp timestamp = Timestamp.from(instant);
        post.setCreatedAt(timestamp);
        post.setUpdatedAt(timestamp);
        Post savedPost = postRepository.save(post);
        return new StandardResponse<>(HttpStatus.OK.value(), "Creación de post exitosa",
                request.getRequestURI(), null, savedPost, null);
    }

    @Override
    public StandardResponse<Post> visit(HttpServletRequest request) {
        int postId = generateRandomPostId();
        Optional<Post> postOptional = postRepository.findById(postId);
        while (!postOptional.isPresent()) {
            postId = generateRandomPostId();
            postOptional = postRepository.findById(postId);
        }
        Post post = postOptional.get();
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        return new StandardResponse<>(HttpStatus.NOT_FOUND.value(), "Post "+postOptional.get().getTitle()+ " visitado con éxito",
                request.getRequestURI(), null, post, null);
    }

    @Override
    public StandardResponse<Collection<CreatePostRequest>> getAllByUser(int userId, int page, int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        StandardResponse<User> forObject = restTemplate.getForObject("http://localhost:9001/user/"+userId, StandardResponse.class);
        logger.info("{}",forObject);
        Collection<CreatePostRequest> createPostRequests = new ArrayList<>();
        if(forObject.getData()==null){
            return new StandardResponse<>(HttpStatus.NOT_FOUND.value(), "Get post by user dont success "+ forObject.getMessage(),
                    request.getRequestURI(), null, null, null);
        }
        Page<Post> postPage = postRepository.findAllByUserId(userId, pageable);
        for (Post post : postPage) {
            CreatePostRequest createPostRequest = new CreatePostRequest();
            createPostRequest.setUserId(userId);
            createPostRequest.setTitle(post.getTitle());
            createPostRequest.setBody(post.getBody());
            createPostRequests.add(createPostRequest);
        }
        return new StandardResponse<>(HttpStatus.OK.value(), "Get post by user success",
                request.getRequestURI(), null, createPostRequests,
                new PageInfo(postPage.getNumber(), postPage.getSize(), postPage.getTotalElements(), postPage.getTotalPages()));
    }

    @Override
    public StandardResponse<Post> findPostById(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return new StandardResponse<>(HttpStatus.OK.value(), "Post found", null, null, post, null);
        } else {
            return new StandardResponse<>(HttpStatus.NOT_FOUND.value(), "\n" +
                    "The post does not exist with the id: " + id, null, null, null, null);
        }
    }

    private int generateRandomPostId() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}
