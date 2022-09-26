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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.EventCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.EventDTO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.EventMapper;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final ClassRoomService classRoomService;
    private final CourseService courseService;
    private final EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, ClassRoomService classRoomService,
                           CourseService courseService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.classRoomService = classRoomService;
        this.courseService = courseService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        LOGGER.info(Messages.TRY_GET_ALL_EVENTS);
        List<Event> events = eventService.getAllEvents();
        LOGGER.info(Messages.OK_GET_ALL_EVENTS, events);
        return new ResponseEntity<>(events.stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_EVENT_BY_ID, id);
        Event event;
        try {
            event = eventService.getEventById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_EVENT_BY_ID, id, event);
        return new ResponseEntity<>(eventMapper.toDTO(event), HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public void deleteEvent(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_EVENT_BY_ID,id);
        try {
            eventService.deleteEventById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_DELETE_EVENT_BY_ID, id);
    }

    @PostMapping("/create/course/{courseId}/classroom/{classRoomId}")
    public ResponseEntity<EventDTO> createEvent(@PathVariable Integer courseId,
                                                @PathVariable Integer classRoomId,
                                                @Valid @RequestBody EventCreateDTO eventCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_EVENT);
        Event createdEvent;
        try {
            Course course = courseService.getCourseById(courseId);
            ClassRoom classRoom = classRoomService.getClassRoomById(classRoomId);
            Event event = eventMapper.fromDTO(eventCreateDTO);
            event.setCourse(course);
            event.setClassRoom(classRoom);
            createdEvent = eventService.createEvent(event);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_EVENT, createdEvent);
        return new ResponseEntity<>(eventMapper.toDTO(createdEvent), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update/course/{courseId}/classroom/{classRoomId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Integer id, @PathVariable Integer courseId,
                                                @PathVariable Integer classRoomId,
                                                @Valid @RequestBody EventCreateDTO eventCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_EVENT, eventCreateDTO);
        Event updatedEvent;
        try {
            eventService.getEventById(id);
            Course course = courseService.getCourseById(courseId);
            ClassRoom classRoom = classRoomService.getClassRoomById(classRoomId);
            Event event = eventMapper.fromDTO(eventCreateDTO);
            event.setCourse(course);
            event.setClassRoom(classRoom);
            event.setId(id);
            updatedEvent = eventService.updateEvent(event);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_EVENT, updatedEvent);
        return new ResponseEntity<>(eventMapper.toDTO(updatedEvent), HttpStatus.OK);
    }

}
