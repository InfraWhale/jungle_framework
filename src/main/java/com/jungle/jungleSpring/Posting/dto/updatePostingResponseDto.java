package com.jungle.jungleSpring.Posting.dto;

import com.jungle.jungleSpring.Posting.entity.Posting;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class updatePostingResponseDto {
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public updatePostingResponseDto(Posting posting) {
        this.title = posting.getTitle();
        this.author = posting.getUsername();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt();
    }
}
