package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomDTO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.ClassRoomMapper;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classrooms")
public class ClassRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomController.class);

    private final ClassRoomService classRoomService;
    private final ClassRoomMapper classRoomMapper;

    @Autowired
    public ClassRoomController (ClassRoomService classRoomService, ClassRoomMapper classRoomMapper) {
        this.classRoomService = classRoomService;
        this.classRoomMapper = classRoomMapper;
    }

    @GetMapping
    public ResponseEntity<List<ClassRoomDTO>> getAllClassRooms() {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS, classRooms);
        return new ResponseEntity<>(classRooms.stream()
                .map(classRoomMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/without-faculty")
    public ResponseEntity<List<ClassRoomDTO>> getAllClassRoomsWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoom> classRooms = classRoomService.getAllClassRoomsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, classRooms);
        return new ResponseEntity<>(classRooms.stream()
                .map(classRoomMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> getClassRoomById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_CLASSROOM_BY_ID, id);
        ClassRoom classRoom;
        try {
            classRoom = classRoomService.getClassRoomById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_CLASSROOM_BY_ID, id, classRoom);
        return new ResponseEntity<>(classRoomMapper.toDTO(classRoom), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteClassRoom(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_CLASSROOM_BY_ID,id);
        classRoomService.deleteClassRoomById(id);
        LOGGER.info(Messages.OK_DELETE_CLASSROOM_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<ClassRoomDTO> createClassRoom(@Valid @RequestBody ClassRoomCreateDTO classRoomCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_CLASSROOM);
        ClassRoom createdClassRoom;
        try {
            createdClassRoom = classRoomService.createClassRoom(classRoomMapper.fromDTO(classRoomCreateDTO));
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_CLASSROOM, createdClassRoom);
        return new ResponseEntity<>(classRoomMapper.toDTO(createdClassRoom), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<ClassRoomDTO> updateClassRoom(@PathVariable Integer id,
                                                        @Valid @RequestBody ClassRoomCreateDTO classRoomCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_CLASSROOM, classRoomCreateDTO);
        ClassRoom updatedClassRoom;
        try {
            classRoomService.getClassRoomById(id);
            ClassRoom classRoom = classRoomMapper.fromDTO(classRoomCreateDTO);
            classRoom.setId(id);
            updatedClassRoom = classRoomService.updateClassRoom(classRoom);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_CLASSROOM, updatedClassRoom);
        return new ResponseEntity<>(classRoomMapper.toDTO(updatedClassRoom), HttpStatus.OK);
    }

}
