package com.jungle.jungleSpring.Posting.dto;

import lombok.Getter;

@Getter
public class updatePostingRequestDto {
    private String title;
    private String content;
    private String author;

    private String password;
}
