package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomDTO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;

public interface ClassRoomService {

    List<ClassRoom> getAllClassRooms();
    List<ClassRoomDTO> getAllClassRoomDTOs();

    List<ClassRoom> getAllClassRoomsWithoutFaculty();
    List<ClassRoomDTO> getAllClassRoomDTOsWithoutFaculty();

    ClassRoom getClassRoomById(Integer id);
    ClassRoomDTO getClassRoomDTOById(Integer id);

    ClassRoom createClassRoom(ClassRoom classRoom);
    ClassRoomDTO createClassRoomDTO(ClassRoomCreateDTO classRoomCreateDTO);

    ClassRoom updateClassRoom(ClassRoom classRoom);
    ClassRoomDTO updateClassRoomDTO(Integer id, ClassRoomCreateDTO classRoomCreateDTO);

    void deleteClassRoomById(Integer id);

}
