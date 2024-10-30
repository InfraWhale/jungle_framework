package com.jungle.jungleSpring.repository;

import com.jungle.jungleSpring.entity.Folder;
import com.jungle.jungleSpring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
}
