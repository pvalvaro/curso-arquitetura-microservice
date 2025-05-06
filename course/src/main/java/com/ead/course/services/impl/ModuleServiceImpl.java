package com.ead.course.services.impl;

import com.ead.course.dtos.ModuleRecordDto;
import com.ead.course.exceptions.ConflictException;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public ModuleModel save(ModuleRecordDto moduleRecordDto, CourseModel courseModel) {
        var moduleModel = new ModuleModel();

        if (moduleRepository.existsByTitle(moduleRecordDto.title())) {
            throw new ConflictException("Error: Module already exists");
        }

        BeanUtils.copyProperties(moduleRecordDto, moduleModel);
        moduleModel.setCourse(courseModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return moduleRepository.save(moduleModel);
    }

    @Override
    public boolean existsTitle(String title) {
        return moduleRepository.existsByTitle(title);
    }


    @Override
    public List<ModuleModel> findAllModulesIntoCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public List<ModuleModel> findAll() {
        return moduleRepository.findAll();
    }

    @Override
    public ModuleModel findById(UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new NotFoundException("Error: Module not found"));
    }

    @Override
    public ModuleModel findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModelsIntoCourse(courseId, moduleId)
                .orElseThrow(() -> new NotFoundException("Error: Module not found for this course"));
    }

    @Transactional
    @Override
    public void delete(ModuleModel moduleModel){
        List<LessonModel> lessonsList = lessonRepository.findAllLensonsIntoModule(moduleModel.getModuleId());
        if(!lessonsList.isEmpty()){
            lessonRepository.deleteAll(lessonsList);
        }
        moduleRepository.delete(moduleModel);
    }

    @Transactional
    @Override
    public ModuleModel update(ModuleRecordDto moduleRecordDto, ModuleModel moduleModel) {
        BeanUtils.copyProperties(moduleRecordDto, moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return moduleRepository.save(moduleModel);
    }

}
