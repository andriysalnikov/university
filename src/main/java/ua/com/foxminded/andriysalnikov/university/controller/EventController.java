package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
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

    @GetMapping("/events")
    public String getAllEvents(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_EVENTS);
        List<Event> events = eventService.getAllEvents();
        LOGGER.info(Messages.OK_GET_ALL_EVENTS, events);
        model.addAttribute("events", events);
        return "event/events";
    }

    @PostMapping("/event/delete")
    public String deleteEvent(@RequestParam("id") Integer eventId, Model model) {
        LOGGER.info(Messages.TRY_DELETE_EVENT_BY_ID, eventId);
        Event deletedEvent;
        try {
            deletedEvent = eventService.deleteEventById(eventId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_EVENT_BY_ID, eventId, deletedEvent);
        return "redirect:/events";
    }

    @GetMapping("/event/create")
    public String getCreationEventModalWindow(Model model) {
        LOGGER.info(Messages.TRY_CREATE_EVENT);
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("classrooms", classRooms);
        model.addAttribute("courses", courses);
        return "event/event_create";
    }

    @PostMapping("/event/create")
    public String createEvent(@RequestParam("date_of_event") String date,
                              @RequestParam("start_time") String time,
                              @RequestParam("course_id") Integer courseId,
                              @RequestParam("classroom_id") Integer classRoomId,
                              Model model) {
        Event createdEvent;
        try {
            createdEvent = eventService.createEvent(
                    collectEventFromParameters(null, date, time,courseId, classRoomId));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_EVENT, createdEvent);
        return "redirect:/events";
    }

    @GetMapping("/event/update")
    public String getUpdationEventModalWindow(@RequestParam("id") Integer eventId, Model model) {
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        List<Course> courses = courseService.getAllCourses();
        Event event;
        try {
            event = eventService.getEventById(eventId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_EVENT, event);
        model.addAttribute("classrooms", classRooms);
        model.addAttribute("courses", courses);
        model.addAttribute("event", event);
        return "event/event_update";
    }

    @PostMapping("/event/update")
    public String updateEvent(@RequestParam("id") Integer eventId,
                              @RequestParam("date_of_event") String date,
                              @RequestParam("start_time") String time,
                              @RequestParam("course_id") Integer courseId,
                              @RequestParam("classroom_id") Integer classRoomId,
                              Model model) {
        Event updatedEvent;
        try {
            updatedEvent = eventService.updateEvent(
                    collectEventFromParameters(eventId, date, time,courseId, classRoomId));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_EVENT, updatedEvent);
        return "redirect:/events";
    }

    private Event collectEventFromParameters(Integer eventId,String date, String time,
                                             Integer courseId, Integer classRoomId) {
        Event event = new Event(
                LocalDate.parse(date),
                LocalTime.parse(time.replace('_', ':')),
                LocalTime.parse(time.replace('_', ':')).plusHours(2),
                classRoomService.getClassRoomById(classRoomId),
                courseService.getCourseById(courseId));
        event.setId(eventId);
        return event;
    }

}
