package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;

public interface TeacherDAO {

    List<Teacher> getAllTeachers();

    Teacher getTeacherById(Integer id);

    Teacher createTeacher(Teacher teacher);

    Teacher deleteTeacherById(Integer id);

    Teacher updateTeacher(Teacher teacher);

}
