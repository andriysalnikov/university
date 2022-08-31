package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyDAO {

    List<Faculty> getAllFaculties();
    Optional<Faculty> getFacultyById(Integer id);
    Optional<Faculty> getFacultyByIdWithClassRooms(Integer id);
    Optional<Faculty> getFacultyByIdWithCourses(Integer id);
    Optional<Faculty> getFacultyByIdWithStudents(Integer id);
    Optional<Faculty> createFaculty(Faculty faculty);
    Optional<Faculty> deleteFacultyById(Integer id);
    Optional<Faculty> updateFaculty(Faculty faculty);

}
