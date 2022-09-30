package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.dto.StudentCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentDTOWithCourses;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();
    List<StudentDTO> getAllStudentDTOs();

    List<Student> getAllStudentsWithoutFaculty();
    List<StudentDTO> getAllStudentDTOsWithoutFaculty();

    Student getStudentById(Integer id);
    StudentDTO getStudentDTOById(Integer id);

    Student getStudentByIdWithCourses(Integer id);
    StudentDTOWithCourses getStudentDTOByIdWithCourses(Integer id);

    Student createStudent(Student student);
    StudentDTO createStudentDTO(StudentCreateDTO studentCreateDTO);

    Student updateStudent(Student student);
    StudentDTO updateStudentDTO(Integer id, StudentCreateDTO studentCreateDTO);

    void deleteStudentById(Integer id);

}
