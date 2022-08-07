package ua.com.foxminded.andriysalnikov.university.utils;

import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestDTOFactory {

    public static List<Course> createListOfCoursesForTest() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Mathematics", "Fundamentals of algebra, geometry and analysis"));
        courses.add(new Course(2, "Physics", "Study of the fundamental laws of the universe"));
        courses.add(new Course(3, "Chemistry", "How to make a moonshine machine"));
        return courses;
    }

    public static List<Event> createListOfAllEventsForTest() {
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, LocalDate.of(2022, 5, 30), LocalTime.of(11, 0), LocalTime.of(13, 0),
                new ClassRoom(1, "101"), new Course(1, "Mathematics", "")));
        events.add(new Event(2, LocalDate.of(2022, 5, 31), LocalTime.of(13, 0), LocalTime.of(15, 0),
                new ClassRoom(2, "102"), new Course(2, "Chemistry", "")));
        events.add(new Event(3, LocalDate.of(2022, 6, 1), LocalTime.of(15, 0), LocalTime.of(17, 0),
                new ClassRoom(3, "102"), new Course(3, "Biology", "")));
        events.add(new Event(4, LocalDate.of(2022, 6, 2), LocalTime.of(17, 0), LocalTime.of(19, 0),
                new ClassRoom(5, "Gym"), new Course(5, "Football", "")));
        return events;
    }

    public static List<Event> createListOfEventsConstrainedByStartDateEndDateForTest() {
        List<Event> events = new ArrayList<>();
        ClassRoom classRoom = new ClassRoom(5, "202");
        Course course = new Course(3, "Ukrainian Language", "");
        events.add(new Event(2, LocalDate.of(2022, 5, 30), LocalTime.of(15, 0), LocalTime.of(17, 0),
                classRoom, course));
        events.add(new Event(4, LocalDate.of(2022, 6, 1), LocalTime.of(15, 0), LocalTime.of(17, 0),
                classRoom, course));
        return events;
    }

    public static TimeTable createTimeTableFromStartDateToEndDateByUser() {
        TimeTable timeTable = new TimeTable();
        List<Event> events = new ArrayList<>();
        events.addAll(createListOfEventsConstrainedByStartDateEndDateForTest());
        events.addAll(createListOfEventsConstrainedByStartDateEndDateForTest());
        events.addAll(createListOfEventsConstrainedByStartDateEndDateForTest());

        events = events
                .stream()
                .sorted(Comparator.comparing(Event::getDayOfEvent)
                        .thenComparing(Event::getStartTime)
                        .thenComparing(Event::getId))
                .collect(Collectors.toList());

        timeTable.setEvents(events);
        return timeTable;
    }

    private TestDTOFactory() { }

}
