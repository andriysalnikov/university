package ua.com.foxminded.andriysalnikov.university.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithoutDependencies;
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
    @JsonView(ViewWithoutDependencies.class)
    public List<ClassRoom> getAllClassRooms() {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS, classRooms);
        return classRooms;
    }

    @GetMapping("/without-faculty")
    @JsonView(ViewWithoutDependencies.class)
    public List<ClassRoom> getAllCoursesWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoom> classRooms = classRoomService.getAllClassRoomsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, classRooms);
        return classRooms;
    }

    @GetMapping("/{id}")
    @JsonView(ViewWithoutDependencies.class)
    public ClassRoom getClassRoomById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_CLASSROOM_BY_ID, id);
        ClassRoom classRoom;
        try {
            classRoom = classRoomService.getClassRoomById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_CLASSROOM_BY_ID, id, classRoom);
        return classRoom;
    }

    @PostMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClassRoom(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_CLASSROOM_BY_ID,id);
        try {
            classRoomService.deleteClassRoomById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_DELETE_CLASSROOM_BY_ID, id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ViewWithoutDependencies.class)
    public ClassRoom createClassRoom(@Valid @RequestBody ClassRoom classRoom) {
        LOGGER.info(Messages.TRY_CREATE_CLASSROOM);
        ClassRoom createdClassRoom;
        try {
            createdClassRoom = classRoomService.createClassRoom(classRoom);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_CLASSROOM, createdClassRoom);
        return createdClassRoom;
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithoutDependencies.class)
    public ClassRoom updateClassRoom(@PathVariable Integer id, @Valid @RequestBody ClassRoom classRoom) {
        LOGGER.info(Messages.TRY_UPDATE_CLASSROOM, classRoom);
        ClassRoom updatedClassRoom;
        try {
            classRoomService.getClassRoomById(id);
            classRoom.setId(id);
            updatedClassRoom = classRoomService.updateClassRoom(classRoom);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_CLASSROOM, updatedClassRoom);
        return updatedClassRoom;
    }

}
