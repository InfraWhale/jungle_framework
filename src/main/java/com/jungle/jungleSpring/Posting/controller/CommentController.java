package com.jungle.jungleSpring.Posting.controller;

import com.jungle.jungleSpring.Posting.dto.comment.AddCommentRequestDto;
import com.jungle.jungleSpring.Posting.dto.comment.AddCommentResponseDto;
import com.jungle.jungleSpring.Posting.dto.comment.UpdateCommentRequestDto;
import com.jungle.jungleSpring.Posting.dto.comment.UpdateCommentResponseDto;
import com.jungle.jungleSpring.Posting.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postingId}")
    public AddCommentResponseDto addPosting(@PathVariable("postingId") Long postingId, @RequestBody AddCommentRequestDto requestDto) {
        return commentService.addComment(postingId, requestDto);
    }

    @PostMapping("/update/{commentId}")
    public UpdateCommentResponseDto updatePosting(@PathVariable("commentId") Long commentId,
                                                  @RequestBody UpdateCommentRequestDto requestDto) {
        return commentService.updateComment(commentId, requestDto);
    }

    @DeleteMapping("/{commentId}")
    public String deletePosting(@PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
