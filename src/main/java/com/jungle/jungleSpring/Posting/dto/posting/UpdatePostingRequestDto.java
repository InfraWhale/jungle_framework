package com.jungle.jungleSpring.Posting.dto.posting;

import lombok.Getter;

@Getter
public class UpdatePostingRequestDto {
    private String title;
    private String content;
    private String author;

    private String password;
}
