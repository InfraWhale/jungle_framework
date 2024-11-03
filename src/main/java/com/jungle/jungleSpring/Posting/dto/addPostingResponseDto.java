package com.jungle.jungleSpring.Posting.dto;

import com.jungle.jungleSpring.Posting.entity.Posting;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class addPostingResponseDto {
    private String title;
    private String author;
    private String content;

    private LocalDateTime createdAt;

    public addPostingResponseDto(Posting posting) {
        this.title = posting.getTitle();
        this.author = posting.getAuthor();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt();
    }
}
