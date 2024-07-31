package com.lgsk.imgreet.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgsk.imgreet.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
