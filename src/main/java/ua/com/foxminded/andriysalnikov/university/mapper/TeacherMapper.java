package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTOWithCourses;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.stream.Collectors;

@Component
public class TeacherMapper {

    public TeacherDTO toDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getId().toString(), teacher.getFirstName(), teacher.getLastName());
    }

    public TeacherDTOWithCourses toDTOWithCourses(Teacher teacher) {
        TeacherDTOWithCourses teacherDTO =
                new TeacherDTOWithCourses(teacher.getId().toString(), teacher.getFirstName(), teacher.getLastName());
        teacherDTO.getCourses().addAll(
                    teacher.getCourses()
                            .stream()
                            .map(Course::getName)
                            .collect(Collectors.toList()));
        return teacherDTO;
    }

    public Teacher fromDTO(TeacherCreateDTO teacherCreateDTO) {
        return new Teacher(teacherCreateDTO.getFirstName(), teacherCreateDTO.getLastName());
    }

}
