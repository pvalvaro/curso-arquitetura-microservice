package com.ead.course.services;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    void delete(LessonModel lesson);

    boolean existsByTitle(String title);

    LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel);

    List<LessonModel> findAll();

    LessonModel findById(UUID lessonId);

    LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel);

    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId);

    Page findAllLessonsIntoModule(Specification<LessonModel> and, Pageable pageable);
}
