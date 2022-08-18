package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Integer id);
    Teacher createTeacher(Teacher teacher);
    Teacher deleteTeacherById(Integer id);
    Teacher updateTeacher(Teacher teacher);
//    List<Course> getTeacherCoursesByTeacherId(Integer id);

}
