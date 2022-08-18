package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;
import java.util.Optional;

public interface ClassRoomDAO {

    List<ClassRoom> getAllClassRooms();
//    List<ClassRoom> getAllClassRoomsWithoutFaculty();
    Optional<ClassRoom> getClassRoomById(Integer id);
    Optional<ClassRoom> createClassRoom(ClassRoom classRoom);
    Optional<ClassRoom> deleteClassRoomById(Integer id);
    Optional<ClassRoom> updateClassRoom(ClassRoom classRoom);
//    Optional<ClassRoom> setFacultyToClassRoom(Integer facultyId, Integer classRoomId);
//    Optional<ClassRoom> removeFacultyFromClassRoom(Integer classRoomId);

}
