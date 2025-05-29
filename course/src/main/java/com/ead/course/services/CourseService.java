package com.ead.course.services;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {
    void delete(CourseModel courseModel);

    boolean existsByName(String name);

    CourseModel findById(UUID courseId);

    CourseModel save(CourseRecordDto courseRecordDto);

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

    CourseModel update(CourseRecordDto courseRecordDto, CourseModel course);
}
