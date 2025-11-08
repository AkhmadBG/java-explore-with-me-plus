package ru.practicum.ewm.main.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.comment.CommentDto;
import ru.practicum.ewm.main.dto.comment.CreateCommentDto;
import ru.practicum.ewm.main.service.comment.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto create(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "eventId") Long eventId,
                             @RequestBody CreateCommentDto createCommentDto) {
        return commentService.create(eventId, userId, createCommentDto);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto update(@PathVariable(name = "userId") Long userId,
                             @PathVariable(name = "commentId") Long commentId,
                             @RequestBody CreateCommentDto createCommentDto) {
        return commentService.update(userId, commentId, createCommentDto);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public void delete(@PathVariable(name = "userId") Long userId,
                       @PathVariable(name = "commentId") Long commentId) {
        commentService.delete(userId, commentId);
    }

    @GetMapping("/{userId}/comments/search")
    public List<CommentDto> findCommentByText(@RequestParam(name = "query", defaultValue = "") String text) {
        return commentService.findCommentByText(text);
    }



}