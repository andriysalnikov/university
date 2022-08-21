package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

    List<Student> getAllStudents();
    List<Student> getAllStudentsWithoutFaculty();
    Optional<Student> getStudentById(Integer id);
    Optional<Student> getStudentByIdWithCourses(Integer id);
    Optional<Student> createStudent(Student student);
    Optional<Student> deleteStudentById(Integer id);
    Optional<Student> updateStudent(Student student);

}
