package com.ead.course.services;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.models.CourseModel;

import java.util.UUID;

public interface CourseService {
    void delete(CourseModel courseModel);
    boolean existsByName(String name);
    CourseModel saveCourse(CourseRecordDto courseRecordDto);
}
