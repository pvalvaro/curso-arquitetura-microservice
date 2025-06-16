package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseRecordDto;
import com.ead.authuser.dtos.UserCourseRecordDto;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
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
public class UserCourseController {
    final CourseClient courseClient;
    final UserCourseService userCourseService;
    final UserService userService;

    public UserCourseController(CourseClient courseClient, UserCourseService userCourseService, UserService userService) {
        this.courseClient = courseClient;
        this.userCourseService = userCourseService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseRecordDto>> getAllCoursesByUser(@PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                     @PathVariable(value = "userId") UUID userId){
        userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                               @RequestBody @Valid UserCourseRecordDto userCourseRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseService.saveSubscriptionUserInCourse(userId, userCourseRecordDto));
    }

    @DeleteMapping("users/courses/{courseId}")
    public ResponseEntity<Object> deleteUserCoursesByCourse(@PathVariable(value = "courseId") UUID courseId){
        if(!userCourseService.existsByCourseId(courseId)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserCourse not found.");
        userCourseService.deleteAllByCourseId(courseId);
        return ResponseEntity.status(HttpStatus.OK).body("UserCourse deleted successfully");
    }
}
