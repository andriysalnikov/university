package ua.com.foxminded.andriysalnikov.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.Optional;


@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Optional<Faculty> getFacultyById(Integer id);

    Integer deleteFacultyById(Integer id);

    @Query(DBConstants.JPQL_GET_FACULTY_BY_ID_WITH_CLASSROOMS)
    Optional<Faculty> getFacultyByIdWithClassRooms(Integer id);

    @Query(DBConstants.JPQL_GET_FACULTY_BY_ID_WITH_COURSES)
    Optional<Faculty> getFacultyByIdWithCourses(Integer id);

    @Query(DBConstants.JPQL_GET_FACULTY_BY_ID_WITH_STUDENTS)
    Optional<Faculty> getFacultyByIdWithStudents(Integer id);

}
