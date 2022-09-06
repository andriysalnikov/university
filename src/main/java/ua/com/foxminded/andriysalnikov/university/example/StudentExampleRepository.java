package ua.com.foxminded.andriysalnikov.university.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.model.Student;

@Repository
public interface StudentExampleRepository extends JpaRepository<Student, Integer> {

    StudentIdAndLastNameProjection getStudentById(Integer id);

    @Query("select new ua.com.foxminded.andriysalnikov.university.model.Student(s.id, s.lastName) " +
            "from Student s where s.id = :id")
    Student getStudentByIdWithQuery(Integer id);

}
