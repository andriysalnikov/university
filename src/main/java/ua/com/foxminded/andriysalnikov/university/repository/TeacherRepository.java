package ua.com.foxminded.andriysalnikov.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> getTeacherById(Integer id);

    @Query(DBConstants.JPQL_GET_TEACHER_BY_ID_WITH_COURSES)
    Optional<Teacher> getTeacherByIdWithCourses(Integer id);

    Integer deleteTeacherById(Integer id);

}