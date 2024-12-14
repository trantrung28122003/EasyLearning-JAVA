package com.hutech.easylearning.repository;


import com.hutech.easylearning.entity.UserNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, String> {
    List<UserNote> findAllByCourseIdAndUserId(String courseId, String userId);
}
