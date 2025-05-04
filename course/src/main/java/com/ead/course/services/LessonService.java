package com.ead.course.services;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    void delete(LessonModel lesson);

    boolean existsByTitle(String title);

    LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel);

    List<LessonModel> findAll();

    Optional<LessonModel> findById(UUID lessonId);

    LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel);

    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);
}
