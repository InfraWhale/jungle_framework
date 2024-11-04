package com.jungle.jungleSpring.Posting.dto.comment;

import com.jungle.jungleSpring.Posting.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponseDto {

    private String content;
    private LocalDateTime createdAt;

    public UpdateCommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
