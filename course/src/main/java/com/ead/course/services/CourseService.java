package com.ead.course.services;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.models.CourseModel;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    void delete(CourseModel courseModel);

    boolean existsByName(String name);

    Optional<CourseModel> findById(UUID courseId);

    CourseModel save(CourseRecordDto courseRecordDto);

    List<CourseModel> findAll();

    CourseModel update(CourseRecordDto courseRecordDto, CourseModel course);
}
