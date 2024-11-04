package com.jungle.jungleSpring.Posting.entity;

import com.jungle.jungleSpring.Posting.dto.comment.AddCommentRequestDto;
import com.jungle.jungleSpring.Posting.dto.comment.UpdateCommentRequestDto;
import com.jungle.jungleSpring.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    public Comment(AddCommentRequestDto requestDto, String loginName, Posting searchedPosting) {
        this.content = requestDto.getContent();
        this.username = loginName;
        this.posting = searchedPosting;
    }

    public void updateComment(UpdateCommentRequestDto requestDto) {
        if (StringUtils.hasText(requestDto.getContent())) {
            this.content = requestDto.getContent();
        }
    }
}
