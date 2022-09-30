package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomDTO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

@Component
public class ClassRoomMapper {

    public ClassRoomDTO toDTO(ClassRoom classRoom) {
        return new ClassRoomDTO(classRoom.getId().toString(), classRoom.getName());
    }

    public ClassRoom fromDTO(ClassRoomCreateDTO classRoomCreateDTO) {
        return new ClassRoom(classRoomCreateDTO.getName());
    }

}
