package com.example.post.repository;

import com.example.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAllByUserId(int userId, Pageable pageable);
}
