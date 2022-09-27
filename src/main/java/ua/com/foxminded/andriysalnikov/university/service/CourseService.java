package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.dto.CourseCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.CourseDTO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();
    List<CourseDTO> getAllCourseDTOs();

    List<Course> getAllCoursesWithoutTeacher();
    List<CourseDTO> getAllCourseDTOsWithoutTeacher();

    List<Course> getAllCoursesWithoutFaculty();
    List<CourseDTO> getAllCourseDTOsWithoutFaculty();

    Course getCourseById(Integer id);
    CourseDTO getCourseDTOById(Integer id);

    Course createCourse(Course course);
    CourseDTO createCourseDTO(CourseCreateDTO courseCreateDTO);

    Course updateCourse(Course course);
    CourseDTO updateCourseDTO(Integer id, CourseCreateDTO courseCreateDTO);

    void deleteCourseById(Integer id);

}
