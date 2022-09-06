package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getAllFaculties();
    Faculty getFacultyById(Integer id);
    Faculty getFacultyByIdWithClassRooms(Integer id);
    Faculty getFacultyByIdWithCourses(Integer id);
    Faculty getFacultyByIdWithStudents(Integer id);
    Faculty createFaculty(Faculty faculty);
    void deleteFacultyById(Integer id);
    Faculty updateFaculty(Faculty faculty);

}
