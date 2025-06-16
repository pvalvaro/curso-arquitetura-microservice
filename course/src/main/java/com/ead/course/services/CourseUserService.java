package com.ead.course.services;

import com.ead.course.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    CourseUserModel saveAndSendSubscriptionUserInCourse(UUID courseId, UUID userId);
    boolean existsByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);
}
