package com.ead.authuser.services;

import com.ead.authuser.dtos.UserCourseRecordDto;
import com.ead.authuser.models.UserCourseModel;

import java.util.UUID;

public interface UserCourseService {
    UserCourseModel saveSubscriptionUserInCourse(UUID userId, UserCourseRecordDto userCourseRecordDto);
}
