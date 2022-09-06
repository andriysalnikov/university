package ua.com.foxminded.andriysalnikov.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Integer> {

    List<ClassRoom> findClassRoomsByFacultyIsNullOrderByIdAsc();

    Optional<ClassRoom> getClassRoomById(Integer id);

    Integer deleteClassRoomById(Integer id);

}
