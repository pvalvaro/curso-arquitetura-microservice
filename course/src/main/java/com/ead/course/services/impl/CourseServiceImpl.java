package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    final CourseRepository courseRepository;
    final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(CourseModel courseModel){
        List<ModuleModel> modulesList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if(!modulesList.isEmpty()){
            for(ModuleModel moduleModel : modulesList){
                List<LessonModel> lessonsList = lessonRepository.findAllLensonsIntoModule(moduleModel.getModuleId());
                if(!lessonsList.isEmpty()){
                    lessonRepository.deleteAll(lessonsList);
                }
            }
            moduleRepository.deleteAll(modulesList);
        }
        courseRepository.delete(courseModel);
    }
}
