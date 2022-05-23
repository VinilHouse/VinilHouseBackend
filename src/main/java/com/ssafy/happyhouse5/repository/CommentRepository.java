package com.ssafy.happyhouse5.repository;

import com.ssafy.happyhouse5.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
