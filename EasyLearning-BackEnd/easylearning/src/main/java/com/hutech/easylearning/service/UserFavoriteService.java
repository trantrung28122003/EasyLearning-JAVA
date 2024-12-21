package com.hutech.easylearning.service;


import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.UserFavorite;
import com.hutech.easylearning.repository.UserFavoriteRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFavoriteService {

    final UserFavoriteRepository userFavoriteRepository;
    final UserService userService;

    public List<Course> getFavoriteCoursesByUserId() {
        var currentUser = userService.getMyInfo();
        return userFavoriteRepository.findByUserId(currentUser.getId())
                .stream()
                .map(UserFavorite::getCourse)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean toggleFavorite(String courseId) {
        var currentUser = userService.getMyInfo();
        Optional<UserFavorite> favoriteOpt = userFavoriteRepository.findByUserIdAndCourseId(currentUser.getId(), courseId);
        if (favoriteOpt.isPresent()) {
            userFavoriteRepository.deleteByUserIdAndCourseId(currentUser.getId(), courseId);
            return false;
        }

        UserFavorite userFavorite = UserFavorite.builder()
                .courseId(courseId)
                .userId(currentUser.getId())
                .changedBy(currentUser.getId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .build();

        userFavoriteRepository.save(userFavorite);
        return true;
    }
}
