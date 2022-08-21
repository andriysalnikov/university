package ua.com.foxminded.andriysalnikov.university.utils;

import ua.com.foxminded.andriysalnikov.university.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestDTOFactory {

    public static Teacher createTeacherWithCoursesForTest() {
        Teacher teacher = new Teacher("Ibragim", "Oganesyan");
        teacher.setId(1);
        createListOfCoursesForTest().forEach(teacher::addCourseToTeacher);
        return teacher;
    }

    public static Student createStudentWithCoursesForTest() {
        Student student = new Student("Zalman", "Obojev");
        student.setId(1);
        createListOfCoursesForTest().forEach(student::addCourseToStudent);
        return student;
    }

    public static List<Course> createListOfCoursesForTest() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Mathematics", "Fundamentals of algebra, geometry and analysis"));
        courses.add(new Course("Physics", "Study of the fundamental laws of the universe"));
        courses.add(new Course("Chemistry", "How to make a moonshine machine"));
        return courses;
    }

    public static List<Event> createListOfAllEventsForTest() {
        List<Event> events = new ArrayList<>();
        events.add(new Event(LocalDate.of(2022, 5, 30), LocalTime.of(11, 0), LocalTime.of(13, 0),
                new ClassRoom("101"), new Course("Mathematics", "")));
        events.add(new Event(LocalDate.of(2022, 5, 31), LocalTime.of(13, 0), LocalTime.of(15, 0),
                new ClassRoom("102"), new Course("Chemistry", "")));
        events.add(new Event(LocalDate.of(2022, 6, 1), LocalTime.of(15, 0), LocalTime.of(17, 0),
                new ClassRoom("102"), new Course("Biology", "")));
        events.add(new Event(LocalDate.of(2022, 6, 2), LocalTime.of(17, 0), LocalTime.of(19, 0),
                new ClassRoom("Gym"), new Course("Football", "")));
        return events;
    }

    public static List<Event> createListOfEventsConstrainedByStartDateEndDateForTest() {
        List<Event> events = new ArrayList<>();
        ClassRoom classRoom = new ClassRoom("202");
        Course course = new Course("Ukrainian Language", "");
        events.add(new Event(LocalDate.of(2022, 5, 30), LocalTime.of(15, 0), LocalTime.of(17, 0),
                classRoom, course));
        events.add(new Event(LocalDate.of(2022, 6, 1), LocalTime.of(15, 0), LocalTime.of(17, 0),
                classRoom, course));
        return events;
    }

    public static TimeTable createTimeTableFromStartDateToEndDate() {
        TimeTable timeTable = new TimeTable();
        List<Event> events = new ArrayList<>();
        events.addAll(createListOfEventsConstrainedByStartDateEndDateForTest());
        events.addAll(createListOfEventsConstrainedByStartDateEndDateForTest());
        events.addAll(createListOfEventsConstrainedByStartDateEndDateForTest());
        events = events
                .stream()
                .sorted(Comparator.comparing(Event::getDayOfEvent)
                        .thenComparing(Event::getStartTime))
                .collect(Collectors.toList());
        timeTable.setEvents(events);
        return timeTable;
    }

    private TestDTOFactory() { }

}
