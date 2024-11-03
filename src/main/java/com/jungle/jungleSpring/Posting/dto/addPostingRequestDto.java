package com.jungle.jungleSpring.Posting.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class addPostingRequestDto {
    private String title;
    private String author;

    private String content;
    private String password;
}
