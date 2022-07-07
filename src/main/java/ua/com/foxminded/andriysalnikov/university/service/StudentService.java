package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;

public interface StudentService {

    Student getStudentById(Integer id);

    List<Course> getStudentCoursesByStudentId(Integer id);

}
