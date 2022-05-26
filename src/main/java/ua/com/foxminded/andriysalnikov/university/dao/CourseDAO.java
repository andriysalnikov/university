package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;

public interface CourseDAO {

    List<Course> getAllCourses();

    Course getCourseById(Integer id);

    Course createCourse(Course course);

    Course deleteCourserById(Integer id);

    Course updateCourse(Course course);

}
