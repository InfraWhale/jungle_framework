package com.jungle.jungleSpring.Posting.repository;

import com.jungle.jungleSpring.Posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {
}
