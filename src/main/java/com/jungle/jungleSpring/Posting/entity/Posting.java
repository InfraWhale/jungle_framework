package com.jungle.jungleSpring.Posting.entity;

import com.jungle.jungleSpring.Posting.dto.posting.AddPostingRequestDto;
import com.jungle.jungleSpring.Posting.dto.posting.UpdatePostingRequestDto;
import com.jungle.jungleSpring.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Posting extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long id;

    private String title;
    private String content;
    private String author;
    private String password;

    private String username;

    @OneToMany(mappedBy = "posting")
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    public Posting(AddPostingRequestDto requestDto, String loginName, String bcryptedPassword) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.author = requestDto.getAuthor();

        this.username = loginName;
        this.password = bcryptedPassword;
    }

    public void updatePosting(UpdatePostingRequestDto requestDto) {
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
