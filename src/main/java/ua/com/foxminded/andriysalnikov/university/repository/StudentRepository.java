package ua.com.foxminded.andriysalnikov.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findStudentsByFacultyIsNullOrderByIdAsc();

    Optional<Student> getStudentById(Integer id);

    Integer deleteStudentById(Integer id);

    @Query(DBConstants.JPQL_GET_STUDENT_BY_ID_WITH_COURSES)
    Optional<Student> getStudentByIdWithCourses(Integer id);

}
