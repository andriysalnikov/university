package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {

    List<Course> getAllCourses();
//    List<Course> getAllCoursesWithoutTeacher();
//    List<Course> getAllCoursesWithoutFaculty();
//    List<Course> getAllOtherAvailableCoursesForStudent(Integer studentId);
    Optional<Course> getCourseById(Integer id);
    Optional<Course> createCourse(Course course);
    Optional<Course> deleteCourserById(Integer id);
    Optional<Course> updateCourse(Course course);
//    Optional<Course> setTeacherToCourse(Integer teacherId, Integer courseId);
//    Optional<Course> removeTeacherFromCourse(Integer courseId);
//    Optional<Course> setFacultyToCourse(Integer facultyId, Integer courseId);
//    Optional<Course> removeFacultyFromCourse(Integer courseId);
//    void setStudentToCourse(Integer studentId, Integer courseId);
//    void removeStudentFromCourse(Integer studentId, Integer courseId);

}
