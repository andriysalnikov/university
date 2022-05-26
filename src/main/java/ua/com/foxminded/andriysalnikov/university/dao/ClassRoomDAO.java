package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;

public interface ClassRoomDAO {

    List<ClassRoom> getAllClassRooms();

    ClassRoom getClassRoomById(Integer id);

    ClassRoom createClassRoom(ClassRoom classRoom);

    ClassRoom deleteClassRoomById(Integer id);

    ClassRoom updateClassRoom(ClassRoom classRoom);

}
