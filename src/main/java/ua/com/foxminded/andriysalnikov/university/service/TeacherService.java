package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.dto.TeacherCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTOWithCourses;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    List<TeacherDTO> getAllTeacherDTOs();

    Teacher getTeacherById(Integer id);
    TeacherDTO getTeacherDTOById(Integer id);

    Teacher getTeacherByIdWithCourses(Integer id);
    TeacherDTOWithCourses getTeacherDTOByIdWithCourses(Integer id);

    Teacher createTeacher(Teacher teacher);
    TeacherDTO createTeacherDTO(TeacherCreateDTO teacherCreateDTO);

    Teacher updateTeacher(Teacher teacher);
    TeacherDTO updateTeacherDTO(Integer id, TeacherCreateDTO teacherCreateDTO);

    void deleteTeacherById(Integer id);

}
