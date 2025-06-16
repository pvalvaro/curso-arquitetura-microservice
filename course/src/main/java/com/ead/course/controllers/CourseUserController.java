package com.ead.course.controllers;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.SubscriptionRecordDto;
import com.ead.course.dtos.UserRecordDto;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CourseUserController {
    final AuthUserClient authUserClient;
    final CourseUserService courseUserService;
    final CourseService courseService;

    public CourseUserController(AuthUserClient authUserClient, CourseUserService courseUserService, CourseService courseService) {
        this.authUserClient = authUserClient;
        this.courseUserService = courseUserService;
        this.courseService = courseService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserRecordDto>> getAllUsersByCourse(
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "courseId") UUID courseId){
        courseService.findById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveAndSendSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionRecordDto subscriptionRecordDto){
       return ResponseEntity.status(HttpStatus.CREATED).body(courseUserService.saveAndSendSubscriptionUserInCourse(courseId, subscriptionRecordDto.userId()));
    }

    @DeleteMapping("courses/users/{userId}")
    public ResponseEntity<Object> deleteCourseUsersByUser(@PathVariable(value = "userId") UUID userId){
        if(!courseUserService.existsByUserId(userId)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CourseUser not found.");
        courseUserService.deleteAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body("CourseUser deleted successfully");
    }
}
