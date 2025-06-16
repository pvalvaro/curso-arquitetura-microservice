package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserCourseRecordDto;
import com.ead.authuser.exceptions.ConflictException;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    final UserCourseRepository userCourseRepository;
    final UserService userService;

    public UserCourseServiceImpl(UserCourseRepository userCourseRepository, UserService userService) {
        this.userCourseRepository = userCourseRepository;
        this.userService = userService;
    }

    @Override
    public UserCourseModel saveSubscriptionUserInCourse(UUID userId, UserCourseRecordDto userCourseRecordDto) {
        UserModel userModel = userService.findById(userId);
        if(userCourseRepository.existsByUserAndCourseId(userModel, userCourseRecordDto.courseId())){
            throw new ConflictException("Error: Subscription already exists!");
        }
        return userCourseRepository.save(userModel.convertToUserCourseModel(userCourseRecordDto.courseId()));
    }

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return userCourseRepository.existsByCourseId(courseId);
    }

    @Transactional
    @Override
    public void deleteAllByCourseId(UUID courseId) {
        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
