package ua.com.foxminded.andriysalnikov.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.TimeTableManagerException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.User;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTableManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableManager.class);

    private final EventService eventService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public TimeTableManager(EventService eventService, StudentService studentService,
                            TeacherService teacherService) {
        this.eventService = eventService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public TimeTable getTimeTableFromStartDateToEndDateByUser(
            LocalDate startDate, LocalDate endDate, User user) {
        LOGGER.debug(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                startDate, endDate, user);
        if (user == null || user.getId() == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_USER);
            throw new TimeTableManagerException(Messages.ERROR_ARGUMENT_USER);
        }
        if (startDate == null || endDate == null) {
            LOGGER.error(Messages.ERROR_DATE_NULL);
            throw new TimeTableManagerException(Messages.ERROR_DATE_NULL);
        }
        if (startDate.isAfter(endDate)) {
            LOGGER.error(Messages.ERROR_STARTDATE_AFTER_ENDDATE);
            throw new TimeTableManagerException(Messages.ERROR_STARTDATE_AFTER_ENDDATE);
        }
        TimeTable timeTable = new TimeTable();
        List<Event> events = new ArrayList<>();
        List<Course> userCourses;
        if (user instanceof Student) {
            userCourses = studentService.getStudentCoursesByStudentId(user.getId());
        } else {
            userCourses = teacherService.getTeacherCoursesByTeacherId(user.getId());
        }
        for (Course course : userCourses) {
            events.addAll(eventService.getAllEventsFromStartDateToEndDateByCourseId(
                    startDate, endDate, course.getId()));
        }
        events = events.stream().sorted(Comparator.comparing(Event::getDayOfEvent)
                .thenComparing(Event::getStartTime).thenComparing(Event::getId)).collect(Collectors.toList());
        timeTable.setEvents(events);
        LOGGER.debug(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                startDate, endDate, user, timeTable);
        return timeTable;
    }

}
