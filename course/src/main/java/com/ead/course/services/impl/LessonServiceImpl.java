package com.ead.course.services.impl;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.exceptions.ConflictException;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {
    final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public LessonModel save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel) {
        var lesson = new LessonModel();

        if (lessonRepository.existsByTitle(lessonRecordDto.title())) {
            throw new ConflictException("Error: Lesson already exists");
        }
        BeanUtils.copyProperties(lessonRecordDto, lesson);
        lesson.setModule(moduleModel);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return lessonRepository.save(lesson);
    }

    @Override
    public boolean existsByTitle(String title) {
        return lessonRepository.existsByTitle(title);
    }

    @Override
    public List<LessonModel> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLensonsIntoModule(moduleId);
    }

    @Override
    public LessonModel findById(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Error: Lesson not found"));
    }

    @Override
    public LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId)
                .orElseThrow(() -> new NotFoundException("Error: Lesson not found for this module"));
    }

    @Override
    public Page findAllLessonsIntoModule(Specification<LessonModel> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void delete(LessonModel lesson) {
        lessonRepository.delete(lesson);
    }

    @Transactional
    @Override
    public LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel) {
        BeanUtils.copyProperties(lessonRecordDto, lessonModel);
        return lessonRepository.save(lessonModel);
    }
}
