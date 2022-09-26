package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.StudentCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentWithCoursesDTO;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        return new StudentDTO(student.getId().toString(), student.getFirstName(), student.getLastName());
    }

    public StudentWithCoursesDTO toDTOWithCourses(Student student) {
        StudentWithCoursesDTO studentDTO =
                new StudentWithCoursesDTO(student.getId().toString(), student.getFirstName(), student.getLastName());
        studentDTO.getCourses().addAll(
                student.getCourses()
                        .stream()
                        .map(Course::getName)
                        .collect(Collectors.toList()));
        return studentDTO;
    }

    public Student fromDTO(StudentCreateDTO studentCreateDTO) {
        return new Student(studentCreateDTO.getFirstName(), studentCreateDTO.getLastName());
    }

}
