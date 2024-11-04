package com.jungle.jungleSpring.Posting.repository;

import com.jungle.jungleSpring.Posting.entity.Comment;
import com.jungle.jungleSpring.Posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
