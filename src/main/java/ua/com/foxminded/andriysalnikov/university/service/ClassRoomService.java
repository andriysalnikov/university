package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;
import java.util.Optional;

public interface ClassRoomService {

    List<ClassRoom> getAllClassRooms();
    ClassRoom getClassRoomById(Integer id);
    List<ClassRoom> getAllClassRoomsWithoutFaculty();
    ClassRoom createClassRoom(ClassRoom classRoom);
    ClassRoom deleteClassRoomById(Integer id);
    ClassRoom updateClassRoom(ClassRoom classRoom);
    ClassRoom setFacultyToClassRoom(Integer facultyId, Integer classRoomId);
    ClassRoom removeFacultyFromClassRoom(Integer classRoomId);

}
