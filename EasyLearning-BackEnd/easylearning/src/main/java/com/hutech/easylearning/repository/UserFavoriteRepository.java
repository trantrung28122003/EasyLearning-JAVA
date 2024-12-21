package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.UserFavorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserFavoriteRepository extends CrudRepository<UserFavorite, String> {
    Optional<UserFavorite> findByUserIdAndCourseId(String userId, String courseId);
    List<UserFavorite> findByUserId(String userId);
    void deleteByUserIdAndCourseId(String userId, String courseId);


}
