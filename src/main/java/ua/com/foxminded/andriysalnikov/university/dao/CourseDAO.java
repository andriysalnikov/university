package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {

    List<Course> getAllCourses();
    Optional<Course> getCourseById(Integer id);
    Optional<Course> createCourse(Course course);
    Optional<Course> deleteCourserById(Integer id);
    Optional<Course> updateCourse(Course course);

}
