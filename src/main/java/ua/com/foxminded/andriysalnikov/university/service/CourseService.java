package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();
    List<Course> getAllCoursesWithoutTeacher();
    List<Course> getAllCoursesWithoutFaculty();
    Course getCourseById(Integer id);
    Course createCourse(Course course);
    Course deleteCourseById(Integer id);
    Course updateCourse(Course course);

}
