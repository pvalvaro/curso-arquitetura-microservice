package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.UserRecordDto;
import com.ead.course.enums.UserStatus;
import com.ead.course.exceptions.ConflictException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {
    final CourseUserRepository courseUserRepository;
    final CourseService courseService;
    final AuthUserClient authUserClient;

    public CourseUserServiceImpl(CourseUserRepository courseUserRepository, AuthUserClient authUserClient, CourseService courseService) {
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
        this.courseService = courseService;
    }
    @Transactional
    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(UUID courseId, UUID userId) {
        CourseModel courseModel = courseService.findById(courseId);
        if(courseUserRepository.existsByCourseAndUserId(courseModel, userId)){
            throw new ConflictException("Error: Subscription already exists!");
        }

        ResponseEntity<UserRecordDto> responseUser = authUserClient.getOneUserById(userId);
        if(responseUser.getBody().userStatus().equals(UserStatus.BLOCKED)){
            throw new ConflictException("Error: User is blocked.");
        }
        CourseUserModel courseUserModel = courseUserRepository.save(courseModel.convertToCourseModel(userId));
        authUserClient.postSubscriptionUserInCourse(courseUserModel.getCourse().getCourseId(), courseUserModel.getUserId());
        return courseUserModel;
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return courseUserRepository.existsByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteAllByUserId(UUID userId) {
        courseUserRepository.deleteAllByUserId(userId);
    }
}
