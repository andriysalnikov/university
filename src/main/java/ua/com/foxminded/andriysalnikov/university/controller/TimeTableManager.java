package ua.com.foxminded.andriysalnikov.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.User;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.service.EventService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class TimeTableManager {

    private final EventService eventService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public TimeTableManager(
            EventService eventService, StudentService studentService, TeacherService teacherService) {
        this.eventService = eventService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public TimeTable getTimeTableFromStartDateToEndDateByUser(
            LocalDate startDate, LocalDate endDate, User user) {

        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("'User' or 'User.id' cannot be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("'StartDate' or 'EndDate' or both cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("'StartDate' cannot be after 'EndDate'");
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

        events.sort(new EventComparatorByDateAndTime());
        timeTable.setEvents(events);

        return timeTable;

    }

    private static class EventComparatorByDateAndTime implements Comparator<Event> {
        @Override
        public int compare(Event o1, Event o2) {
            if (o1.getDayOfEvent().isBefore(o2.getDayOfEvent())) {
                return -1;
            } else if (o1.getDayOfEvent().isAfter(o2.getDayOfEvent())) {
                return 1;
            } else {
                if (o1.getStartTime().isBefore(o2.getStartTime())) {
                    return -1;
                } else if (o1.getStartTime().isAfter(o2.getStartTime())) {
                    return 1;
                }
            }
            return 0;
        }
    }

}
