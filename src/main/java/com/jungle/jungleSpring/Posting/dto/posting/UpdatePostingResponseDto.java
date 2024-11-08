package com.jungle.jungleSpring.Posting.dto.posting;

import com.jungle.jungleSpring.Posting.entity.Posting;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostingResponseDto {
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public UpdatePostingResponseDto(Posting posting) {
        this.title = posting.getTitle();
        this.author = posting.getUsername();
        this.content = posting.getContent();
        this.createdAt = posting.getCreatedAt();
    }
}
