package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();
//    List<Course> getAllCoursesWithoutTeacher();
//    List<Course> getAllCoursesWithoutFaculty();
//    List<Course> getAllOtherAvailableCoursesForStudent(Integer studentId);
    Course getCourseById(Integer id);
    Course createCourse(Course course);
    Course deleteCourseById(Integer id);
    Course updateCourse(Course course);
//    Course setTeacherToCourse(Integer teacherId, Integer courseId);
//    Course removeTeacherFromCourse(Integer courseId);
//    Course setFacultyToCourse(Integer facultyId, Integer courseId);
//    Course removeFacultyFromCourse(Integer courseId);
//    void setStudentToCourse(Integer studentId, Integer courseId);
//    void removeStudentFromCourse(Integer studentId, Integer courseId);

}
