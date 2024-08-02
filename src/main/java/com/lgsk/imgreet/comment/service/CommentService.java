package com.lgsk.imgreet.comment.service;

import com.lgsk.imgreet.comment.dto.CommentDTO;
import com.lgsk.imgreet.comment.dto.CommentResponseDTO;
import com.lgsk.imgreet.comment.dto.CreateCommentRequestDTO;
import com.lgsk.imgreet.comment.dto.DeleteCommentRequestDTO;
import com.lgsk.imgreet.comment.repository.CommentRepository;
import com.lgsk.imgreet.entity.Comment;
import com.lgsk.imgreet.entity.Greet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentResponseDTO saveComment(CreateCommentRequestDTO dto) {
        Greet greet = Greet.builder()
                .id(dto.getGreetId()).build();
        Comment saved = commentRepository.save(Comment.builder()
                .greet(greet)
                .ipAddress(dto.getIpAddress())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .content(dto.getContent())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build());

        return CommentResponseDTO.builder()
                .id(saved.getId())
                .greet(saved.getGreet())
                .ipAddress(saved.getIpAddress())
                .nickname(saved.getNickname())
                .password(saved.getPassword())
                .content(saved.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public CommentDTO getCommentById(Long id) throws NullPointerException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new NullPointerException();
        }
        return CommentDTO.builder()
                .id(optionalComment.get().getId())
                .greet(optionalComment.get().getGreet())
                .ipAddress(optionalComment.get().getIpAddress())
                .nickname(optionalComment.get().getNickname())
                .password(optionalComment.get().getPassword())
                .content(optionalComment.get().getContent())
                .build();
    }

    public void deleteCommentById(DeleteCommentRequestDTO dto) {
        CommentDTO comment = getCommentById(dto.getCommentId());
        if (!dto.getPassword().equals(comment.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }
        commentRepository.deleteById(dto.getCommentId());
    }

    public List<CommentResponseDTO> getCommentsByGreetId(Long greetId) {
        return commentRepository.findByGreet_Id(greetId).stream().map(e ->
                        CommentResponseDTO.builder()
                                .id(e.getId())
                                .greet(e.getGreet())
                                .ipAddress(e.getIpAddress())
                                .nickname(e.getNickname())
                                .password(e.getPassword())
                                .content(e.getContent())
                                .createdDate(e.getCreatedDate())
                                .build())
                .toList();
    }
}
