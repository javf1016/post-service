package com.example.post.controller;

import com.example.post.model.CreatePostRequest;
import com.example.post.model.Post;
import com.example.post.model.StandardResponse;
import com.example.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/***
 *  MATCH CONTROLLER
 * ***/
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;
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
	public StandardResponse<Collection<CreatePostRequest>> getAllPostsByUserId(@PathVariable int userId,
																			   @RequestParam(defaultValue = "0") int page,
																			   @RequestParam(defaultValue = "10") int size,
																			   HttpServletRequest request) {
		return postService.getAllByUser(userId, page, size, request);
	}


	/**
	 * Add a comment to a post
	 */
	/*@PostMapping("/{postId}/comments")
	public StandardResponse<Comment> addComment(@PathVariable("postId") Integer postId,
										   @RequestBody AddCommentRequest addCommentRequest,
										   HttpServletRequest request) {
			StandardResponse<User> author = userService.findUserById(addCommentRequest.getAuthorId());
			StandardResponse<Post> post = postService.findPostById(postId);
			return postService.addComment(author.getData(), post.getData(), addCommentRequest.getBody(), request);
	}
*/
}
