package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.dto.*;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getAllFaculties();
    List<FacultyDTO> getAllFacultyDTOs();

    Faculty getFacultyById(Integer id);
    FacultyDTO getFacultyDTOById(Integer id);

    Faculty getFacultyByIdWithClassRooms(Integer id);
    FacultyDTOWithClassRooms getFacultyDTOByIdWithClassRooms(Integer id);

    Faculty getFacultyByIdWithCourses(Integer id);
    FacultyDTOWithCourses getFacultyDTOByIdWithCourses(Integer id);

    Faculty getFacultyByIdWithStudents(Integer id);
    FacultyDTOWithStudents getFacultyDTOByIdWithStudents(Integer id);

    Faculty createFaculty(Faculty faculty);
    FacultyDTO createFacultyDTO(FacultyCreateDTO facultyCreateDTO);

    Faculty updateFaculty(Faculty faculty);
    FacultyDTO updateFacultyDTO(Integer id, FacultyCreateDTO facultyCreateDTO);

    void deleteFacultyById(Integer id);

}
