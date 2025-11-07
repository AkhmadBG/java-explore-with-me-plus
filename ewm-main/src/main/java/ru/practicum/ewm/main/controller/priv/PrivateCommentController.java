package ru.practicum.ewm.main.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.comment.CommentDto;
import ru.practicum.ewm.main.dto.comment.CreateCommentDto;
import ru.practicum.ewm.main.service.comment.CommentService;

@RestController
@RequiredArgsConstructor
@Validated
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto create(Long userId, Long eventId, CreateCommentDto createCommentDto) {
        return commentService.create(eventId, userId, createCommentDto);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto update(Long userId, Long commentId, CreateCommentDto createCommentDto) {
        return commentService.update(userId, commentId, createCommentDto);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(Long userId, Long commentId) {
        commentService.delete(userId, commentId);
    }
}