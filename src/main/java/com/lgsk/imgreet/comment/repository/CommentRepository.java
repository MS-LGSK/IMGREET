package com.lgsk.imgreet.comment.repository;

import com.lgsk.imgreet.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByGreet_Id(Long greetId);
}
