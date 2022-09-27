package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomDTO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomController.class);

    private final ClassRoomService classRoomService;

    @Autowired
    public ClassRoomController (ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @GetMapping
    public ResponseEntity<List<ClassRoomDTO>> getAllClassRooms() {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoomDTO> classRoomDTOs = classRoomService.getAllClassRoomDTOs();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS, classRoomDTOs);
        return new ResponseEntity<>(classRoomDTOs, HttpStatus.OK);
    }

    @GetMapping("/without-faculty")
    public ResponseEntity<List<ClassRoomDTO>> getAllClassRoomsWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoomDTO> classRoomDTOs = classRoomService.getAllClassRoomDTOsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, classRoomDTOs);
        return new ResponseEntity<>(classRoomDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> getClassRoomById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_CLASSROOM_BY_ID, id);
        ClassRoomDTO classRoomDTO = classRoomService.getClassRoomDTOById(id);
        LOGGER.info(Messages.OK_GET_CLASSROOM_BY_ID, id, classRoomDTO);
        return new ResponseEntity<>(classRoomDTO, HttpStatus.OK);
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
        ClassRoomDTO createdClassRoomDTO =
                classRoomService.createClassRoomDTO(classRoomCreateDTO);
        LOGGER.info(Messages.OK_CREATE_CLASSROOM, createdClassRoomDTO);
        return new ResponseEntity<>(createdClassRoomDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<ClassRoomDTO> updateClassRoom(@PathVariable Integer id,
                                                        @Valid @RequestBody ClassRoomCreateDTO classRoomCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_CLASSROOM, classRoomCreateDTO);
        classRoomService.getClassRoomById(id);
        ClassRoomDTO updatedClassRoomDTO =
                classRoomService.updateClassRoomDTO(id, classRoomCreateDTO);
        LOGGER.info(Messages.OK_UPDATE_CLASSROOM, updatedClassRoomDTO);
        return new ResponseEntity<>(updatedClassRoomDTO, HttpStatus.OK);
    }

}
