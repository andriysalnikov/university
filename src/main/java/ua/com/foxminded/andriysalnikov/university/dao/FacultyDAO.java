package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;
import java.util.Optional;

public interface FacultyDAO {

    List<Faculty> getAllFaculties();
    Optional<Faculty> getFacultyById(Integer id);
    Optional<Faculty> createFaculty(Faculty faculty);
    Optional<Faculty> deleteFacultyById(Integer id);
    Optional<Faculty> updateFaculty(Faculty faculty);
    List<Course> getFacultyCoursesByFacultyId(Integer id);
    List<ClassRoom> getFacultyClassRoomsByFacultyId(Integer id);
    List<Student> getFacultyStudentsByFacultyId(Integer id);

}
