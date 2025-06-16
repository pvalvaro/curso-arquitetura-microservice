package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
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
public class CourseServiceImpl implements CourseService {
    final CourseRepository courseRepository;
    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;
    final CourseUserRepository courseUserRepository;
    final AuthUserClient authUserClient;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository, CourseUserRepository courseUserRepository, AuthUserClient authUserClient) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
    }

    @Override
    public CourseModel save(CourseRecordDto courseRecordDto) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }

    @Override
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public CourseModel findById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Error: Course not found"));
    }

    @Transactional
    @Override
    public void delete(CourseModel courseModel){
        boolean deleteCourseUserInAuthUser = false;
        List<ModuleModel> modulesList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if(!modulesList.isEmpty()){
            for(ModuleModel moduleModel : modulesList){
                List<LessonModel> lessonsList = lessonRepository.findAllLensonsIntoModule(moduleModel.getModuleId());
                if(!lessonsList.isEmpty()){
                    lessonRepository.deleteAll(lessonsList);
                    deleteCourseUserInAuthUser = true;
                }
            }
            moduleRepository.deleteAll(modulesList);
            if(deleteCourseUserInAuthUser) authUserClient.deleteCourseUserInAuthUser(courseModel.getCourseId());
        }

        List<CourseUserModel> courseUserModelList = courseUserRepository.findAllUsersIntoCourse(courseModel.getCourseId());
        if(!courseUserModelList.isEmpty()) courseUserRepository.deleteAll(courseUserModelList);

        courseRepository.delete(courseModel);
    }

    @Transactional
    @Override
    public CourseModel update(CourseRecordDto courseRecordDto, CourseModel courseModel) {
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }
}
