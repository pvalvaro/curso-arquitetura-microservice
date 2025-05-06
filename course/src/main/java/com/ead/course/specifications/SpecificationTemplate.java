package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {
    @And({
            @Spec(path = "name", spec = Equal.class),
            @Spec(path = "description", spec = Equal.class),
            @Spec(path = "courseLevel", spec = Like.class),
            @Spec(path = "courseStatus", spec = Like.class),
            @Spec(path = "userInstructor", spec = LikeIgnoreCase.class)
    })
    public interface CourseSpec extends Specification<CourseModel>{}
}
