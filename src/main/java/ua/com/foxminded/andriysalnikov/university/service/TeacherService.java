package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher getTeacherById(Integer id);

    List<Course> getTeacherCoursesByTeacherId(Integer id);

}
