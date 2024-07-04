package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.UserResponse;
import com.hutech.easylearning.dto.request.UserCreationRequest;
import com.hutech.easylearning.dto.request.UserUpdateRequest;
import com.hutech.easylearning.entity.User;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.mapper.UserMapper;
import com.hutech.easylearning.repository.RoleRepository;
import com.hutech.easylearning.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired


    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    //@PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method getUsers");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }



    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo()
    {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        var user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);

        var userImageUrl = "defalut";
        if(request.getImageUrl() != null)
        {
           // userImageUrl = _fileService.SaveFile(request.Avatar);
        }
        user.setImageUrl(userImageUrl);
        user.setDateCreate(LocalDateTime.now());
        user.setDateChange(LocalDateTime.now());
        user.setIsDeleted(false);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if(userRepository.existsByUserName(user.getUserName()))
        {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        List<String> defaultRoleNames = List.of("USER");
        var roles = roleRepository.findByNameIn(defaultRoleNames);
        user.setRoles(new HashSet<>(roles));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }
    public UserResponse updateUser(String userId , UserUpdateRequest request) {
        User getUserById = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(getUserById, request);
        getUserById.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findByNameIn((request.getRoles()));

        getUserById.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(getUserById));
    }
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
