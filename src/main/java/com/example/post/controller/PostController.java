package com.example.post.controller;

import com.example.post.model.CreatePostRequest;
import com.example.post.model.PageInfo;
import com.example.post.model.Post;
import com.example.post.model.StandardResponse;
import com.example.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

/***
 *  MATCH CONTROLLER
 * ***/
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	private Logger logger = LoggerFactory.getLogger(PostController.class);

    /**
	 * Create a post with a given user id in the request
	 **/
	@PostMapping("/createpost")
	public StandardResponse createPost(@RequestBody CreatePostRequest requestPost, HttpServletRequest request) {
		return postService.createPost(requestPost, request);
	}


    /***
     *
     * Get a post's information and register a visit to it
     *
     * **/
	@PostMapping("/visit")
	public StandardResponse<Post> visitPost(HttpServletRequest request) {
		return postService.visit(request);
	}

    /**
     *
     * Get all posts made by a given user (paginated)
     *
     * **/
	@GetMapping("/listbyuser/{userId}")
	@CircuitBreaker(name = "getAllPostsBreaker", fallbackMethod = "getAllPostsFallback")
	public StandardResponse<Collection<CreatePostRequest>> getAllPostsByUserId(@PathVariable int userId,
																			   @RequestParam(defaultValue = "0") int page,
																			   @RequestParam(defaultValue = "10") int size,
																			   HttpServletRequest request) {
		return postService.getAllByUser(userId, page, size, request);
	}

	/**
	 * Fallback METHOD getAllPostsFallBack
	 */
	public StandardResponse<Collection<CreatePostRequest>> getAllPostsFallback(Exception ex){
		logger.info("Fallback is excecute because server is down: "+ex.getMessage());
		Collection<CreatePostRequest> createPostRequests = new ArrayList<>();
		CreatePostRequest createPostRequest = CreatePostRequest.builder()
				.userId(123456)
				.title("post dummy")
				.body("dummy create service down")
				.build();
		createPostRequests.add(createPostRequest);
		return new StandardResponse<>(HttpStatus.SERVICE_UNAVAILABLE.value(), "Sevice down",
				null, null, createPostRequests, null);
	}


	/**
	 * Find a post by PostId
	 */
	@GetMapping("/{postId}")
	public StandardResponse<Post> getSinglePost(@PathVariable Integer postId, HttpServletRequest request) {
			return postService.getPost(postId, request);
	}
}
