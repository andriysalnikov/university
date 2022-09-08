package ua.com.foxminded.andriysalnikov.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.model.*;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTableManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableManager.class);

    private final EventService eventService;

    @Autowired
    public TimeTableManager(EventService eventService) {
        this.eventService = eventService;
    }

    public TimeTable getTimeTableFromStartDateToEndDateByTeacher(
            LocalDate startDate, LocalDate endDate, Teacher teacher) {
        LOGGER.debug(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER,
                startDate, endDate);
        List<Course> courses = teacher.getCourses();
        TimeTable timeTable = getTimeTableFromStartDateToEndDate(startDate, endDate, courses);
        LOGGER.debug(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER,
                startDate, endDate, timeTable);
        return timeTable;
    }

    public TimeTable getTimeTableFromStartDateToEndDateByStudent(
            LocalDate startDate, LocalDate endDate, Student student) {
        LOGGER.debug(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT,
                startDate, endDate);
        List<Course> courses = student.getCourses();
        TimeTable timeTable = getTimeTableFromStartDateToEndDate(startDate, endDate, courses);
        LOGGER.debug(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT,
                startDate, endDate, timeTable);
        return timeTable;
    }

    private TimeTable getTimeTableFromStartDateToEndDate(
            LocalDate startDate, LocalDate endDate, List<Course> courses) {
        Validation.validateDate(startDate, endDate);
        TimeTable timeTable = new TimeTable();
        List<Event> events = new ArrayList<>();
        for (Course course : courses) {
            events.addAll(eventService.getAllEventsFromStartDateToEndDateByCourseId(
                    startDate, endDate, course.getId()));
        }
        events = events.stream().sorted(Comparator.comparing(Event::getDayOfEvent)
                .thenComparing(Event::getStartTime)).collect(Collectors.toList());
        timeTable.setEvents(events);
        return timeTable;
    }

}
