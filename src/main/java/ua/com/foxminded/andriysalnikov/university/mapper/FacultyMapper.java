package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.*;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.stream.Collectors;

@Component
public class FacultyMapper {

    public FacultyDTO toDTO(Faculty faculty) {
        return new FacultyDTO(faculty.getId().toString(), faculty.getFullName());
    }

    public FacultyWithCoursesDTO toDTOWithCourses(Faculty faculty) {
        FacultyWithCoursesDTO facultyDTO =
                new FacultyWithCoursesDTO(faculty.getId().toString(), faculty.getFullName());
        facultyDTO.getCourses().addAll(
                    faculty.getCourses()
                            .stream()
                            .map(Course::getName)
                            .collect(Collectors.toList()));
        return facultyDTO;
    }

    public FacultyWithClassRoomsDTO toDTOWithClassRooms(Faculty faculty) {
        FacultyWithClassRoomsDTO facultyDTO =
                new FacultyWithClassRoomsDTO(faculty.getId().toString(), faculty.getFullName());
        facultyDTO.getClassRooms().addAll(
                faculty.getClassRooms()
                        .stream()
                        .map(ClassRoom::getName)
                        .collect(Collectors.toList()));
        return facultyDTO;
    }

    public FacultyWithStudentsDTO toDTOWithStudents(Faculty faculty) {
        FacultyWithStudentsDTO facultyDTO =
                new FacultyWithStudentsDTO(faculty.getId().toString(), faculty.getFullName());
        facultyDTO.getStudents().addAll(
                faculty.getStudents()
                        .stream()
                        .map(student -> student.getFirstName() + " " + student.getLastName())
                        .collect(Collectors.toList()));
        return facultyDTO;
    }

    public Faculty fromDTO(FacultyCreateDTO facultyCreateDTO) {
        return new Faculty(facultyCreateDTO.getFullName());
    }

}
