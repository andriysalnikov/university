package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getAllFaculties();
    Faculty getFacultyById(Integer id);
    Faculty createFaculty(Faculty faculty);
    Faculty deleteFacultyById(Integer id);
    Faculty updateFaculty(Faculty faculty);
//    List<Course> getFacultyCoursesByFacultyId(Integer id);
//    List<ClassRoom> getFacultyClassRoomsByFacultyId(Integer id);
//    List<Student> getFacultyStudentsByFacultyId(Integer id);

}
