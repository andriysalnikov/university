package ua.com.foxminded.andriysalnikov.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findCoursesByFacultyIsNullOrderByIdAsc();

    List<Course> findCoursesByTeacherIsNullOrderByIdAsc();

    Optional<Course> getCourseById(Integer id);

    Integer deleteCourseById(Integer id);

}
