package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.User;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Integer id);
    Teacher createTeacher(User user);
    Teacher deleteTeacherById(Integer id);
    Teacher updateTeacher(User user);
    List<Course> getTeacherCoursesByTeacherId(Integer id);

}
