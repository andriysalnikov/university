package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.CourseCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.CourseDTO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId().toString(), course.getName(), course.getDescription());
    }

    public Course fromDTO(CourseCreateDTO courseCreateDTO) {
        return new Course(courseCreateDTO.getName(), courseCreateDTO.getDescription());
    }

}
