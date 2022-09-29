package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.EventCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.EventDTO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final ClassRoomService classRoomService;
    private final CourseService courseService;

    @Autowired
    public EventController(EventService eventService, ClassRoomService classRoomService,
                           CourseService courseService) {
        this.eventService = eventService;
        this.classRoomService = classRoomService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        LOGGER.info(Messages.TRY_GET_ALL_EVENTS);
        List<EventDTO> eventDTOs = eventService.getAllEventDTOs();
        LOGGER.info(Messages.OK_GET_ALL_EVENTS, eventDTOs);
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_EVENT_BY_ID, id);
        EventDTO eventDTO = eventService.getEventDTOById(id);
        LOGGER.info(Messages.OK_GET_EVENT_BY_ID, id, eventDTO);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_EVENT_BY_ID,id);
        eventService.deleteEventById(id);
        LOGGER.info(Messages.OK_DELETE_EVENT_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create/course/{courseId}/classroom/{classRoomId}")
    public ResponseEntity<EventDTO> createEvent(@PathVariable Integer courseId,
                                                @PathVariable Integer classRoomId,
                                                @Valid @RequestBody EventCreateDTO eventCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_EVENT);
        Course course = courseService.getCourseById(courseId);
        ClassRoom classRoom = classRoomService.getClassRoomById(classRoomId);
        EventDTO createdEventDTO = eventService.createEventDTO(eventCreateDTO, course, classRoom);
        LOGGER.info(Messages.OK_CREATE_EVENT, createdEventDTO);
        return new ResponseEntity<>(createdEventDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update/course/{courseId}/classroom/{classRoomId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Integer id, @PathVariable Integer courseId,
                                                @PathVariable Integer classRoomId,
                                                @Valid @RequestBody EventCreateDTO eventCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_EVENT, eventCreateDTO);
        eventService.getEventById(id);
        Course course = courseService.getCourseById(courseId);
        ClassRoom classRoom = classRoomService.getClassRoomById(classRoomId);
        EventDTO updatedEventDTO = eventService.updateEventDTO(id, eventCreateDTO, course, classRoom);
        LOGGER.info(Messages.OK_UPDATE_EVENT, updatedEventDTO);
        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }

}
