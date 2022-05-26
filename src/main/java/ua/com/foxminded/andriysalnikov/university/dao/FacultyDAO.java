package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.List;

public interface FacultyDAO {

    List<Faculty> getAllFaculties();

    Faculty getFacultyById(Integer id);

    Faculty createFaculty(Faculty faculty);

    Faculty deleteFacultyById(Integer id);

    Faculty updateFaculty(Faculty faculty);

}
