package com.ead.course.services.impl;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {
    final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(LessonModel lesson) {
        lessonRepository.delete(lesson);
    }

    @Override
    public boolean existsByTitle(String title) {
        return lessonRepository.existsByTitle(title);
    }

    @Override
    public LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel) {
        var lesson = new LessonModel();
        BeanUtils.copyProperties(lessonRecordDto, lesson);
        lesson.setModule(moduleModel);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return lessonRepository.save(lesson);
    }

    @Override
    public List<LessonModel> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Optional<LessonModel> findById(UUID lessonId) {
        return lessonRepository.findById(lessonId);
    }

    @Override
    public LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel) {
        BeanUtils.copyProperties(lessonRecordDto, lessonModel);
        return lessonRepository.save(lessonModel);
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLensonsIntoModule(moduleId);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        Optional<LessonModel> lessonModel = lessonRepository.findLessonIntoModule(moduleId, lessonId);
        if(lessonModel.isEmpty()){
            // exceptions
        }
        return lessonModel;
    }
}
