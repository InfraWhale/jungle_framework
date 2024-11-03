package com.jungle.jungleSpring.Posting.entity;

import com.jungle.jungleSpring.Posting.dto.addPostingRequestDto;
import com.jungle.jungleSpring.Posting.dto.updatePostingRequestDto;
import com.jungle.jungleSpring.common.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor
public class Posting extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;
    private String password;

    private String username;

    public Posting(addPostingRequestDto requestDto, String loginName, String bcryptedPassword) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();

        this.username = loginName;
        this.password = bcryptedPassword;
    }

    public void updatePosting(updatePostingRequestDto requestDto) {
        if (StringUtils.hasText(requestDto.getTitle())) {
            this.title = requestDto.getTitle();
        }

        if (StringUtils.hasText(requestDto.getContent())) {
            this.content = requestDto.getContent();
        }

        if (StringUtils.hasText(requestDto.getAuthor())) {
            this.author = requestDto.getAuthor();
        }
    }
}
