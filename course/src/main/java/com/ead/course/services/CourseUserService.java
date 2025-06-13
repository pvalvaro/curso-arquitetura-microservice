package com.ead.course.services;

import com.ead.course.models.CourseUserModel;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface CourseUserService {
    CourseUserModel saveAndSendSubscriptionUserInCourse(UUID courseId, UUID userId);
}
