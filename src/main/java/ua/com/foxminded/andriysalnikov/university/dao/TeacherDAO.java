package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherDAO {

    List<Teacher> getAllTeachers();
    Optional<Teacher> getTeacherById(Integer id);
    Optional<Teacher> getTeacherByIdWithCourses(Integer id);
    Optional<Teacher> createTeacher(Teacher teacher);
    Optional<Teacher> deleteTeacherById(Integer id);
    Optional<Teacher> updateTeacher(Teacher teacher);

}
