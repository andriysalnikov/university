package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;

public interface ClassRoomService {

    List<ClassRoom> getAllClassRooms();
    List<ClassRoom> getAllClassRoomsWithoutFaculty();
    ClassRoom getClassRoomById(Integer id);
    ClassRoom createClassRoom(ClassRoom classRoom);
    ClassRoom deleteClassRoomById(Integer id);
    ClassRoom updateClassRoom(ClassRoom classRoom);

}
