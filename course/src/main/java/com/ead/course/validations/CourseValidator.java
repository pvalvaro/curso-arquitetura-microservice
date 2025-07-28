package com.ead.course.validations;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {
    Logger logger = LogManager.getLogger(CourseValidator.class);
    private final Validator validator;
    private final CourseService courseService;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, CourseService courseService) {
        this.validator = validator;
        this.courseService = courseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseRecordDto courseRecordDto = (CourseRecordDto) o;
        validator.validate(courseRecordDto, errors);

        if(errors.hasErrors()) return;

        validateCourseName(courseRecordDto, errors);
        validateInstructor(courseRecordDto.userInstructor(), errors);
    }

    private void validateCourseName(CourseRecordDto courseRecordDto, Errors errors) {
        if(courseService.existsByName(courseRecordDto.name())) {
            errors.rejectValue("name", "courseNameConflict", "Course name already exists");
            logger.error("Error validation courseName {}", courseRecordDto.name());
        }
    }

    private void validateInstructor(UUID userInstructor, Errors errors) {
        /*ResponseEntity<UserRecordDto> responseUser = authUserClient.getOneUserById(userInstructor);
        if(responseUser.getBody().userType().equals(UserType.STUDENT) ||
            responseUser.getBody().userType().equals(UserType.USER)){
            errors.rejectValue("userInstructor", "userInstructorError", "User must be INSTRUCTOR or ADMIN.");
            logger.error("Error validation userInstructor {}", userInstructor);
        }

        if(responseUser.getBody().userStatus().equals(UserStatus.BLOCKED)){
            errors.rejectValue("userInstructor", "userInstructorBlocked", "User is BLOCKED");
            logger.error("Error validation userInstructor {}", userInstructor);
        }*/
    }
}
