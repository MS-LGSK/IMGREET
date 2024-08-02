package com.lgsk.imgreet.comment.controller;

import com.lgsk.imgreet.base.commonUtil.Rq;
import com.lgsk.imgreet.comment.dto.CommentResponseDTO;
import com.lgsk.imgreet.comment.dto.CreateCommentRequestDTO;
import com.lgsk.imgreet.comment.dto.DeleteCommentRequestDTO;
import com.lgsk.imgreet.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final Rq rq;

    @ResponseBody
    @GetMapping("/greet/{id}/commentList")
    public ResponseEntity<Object> getAllComments(@PathVariable Long id) {
        List<CommentResponseDTO> comments = commentService.getCommentsByGreetId(id);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    @ResponseBody
    @PostMapping("/greet/{id}/comment")
    public ResponseEntity<Object> writeComment(@PathVariable("id") Long id, @RequestBody @Valid CreateCommentRequestDTO dto) {
        Map<String, Object> responseMap = new HashMap<>();

        String ipAddress = rq.getClientIpAddress();
        dto.setGreetId(id);
        dto.setIpAddress(ipAddress);

        CommentResponseDTO commentResponseDTO = commentService.saveComment(dto);
        if (commentResponseDTO == null) {
            responseMap.put("success", false);
            responseMap.put("message", "댓글 저장에 실패했습니다.");
            return ResponseEntity.badRequest().body(responseMap);
        }
        responseMap.put("success", true);
        responseMap.put("message", "댓글을 성공적으로 저장했습니다.");

        return ResponseEntity.accepted().body(responseMap);
    }

    @PostMapping("/greet/comment/delete")
    public ResponseEntity<Object> deleteComment(@RequestBody @Valid DeleteCommentRequestDTO dto) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            commentService.deleteCommentById(dto);
        } catch (RuntimeException e) {
            responseMap.put("success", false);
            responseMap.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }
        responseMap.put("success", true);
        responseMap.put("message", "성공적으로 댓글을 삭제했습니다.");
        return ResponseEntity.ok(responseMap);
    }
}
