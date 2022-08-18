package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();
//    List<Student> getAllStudentsWithoutFaculty();
    Student getStudentById(Integer id);
    Student createStudent(Student student);
    Student deleteStudentById(Integer id);
    Student updateStudent(Student student);
//    List<Course> getStudentCoursesByStudentId(Integer id);
//    Student setFacultyToStudent(Integer facultyId, Integer studentId);
//    Student removeFacultyFromStudent(Integer studentId);

}
