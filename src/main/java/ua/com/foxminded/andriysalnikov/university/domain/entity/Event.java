package ua.com.foxminded.andriysalnikov.university.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Event {

    private Integer id;
    private LocalDate dayOfEvent;
    private LocalTime startTime;
    private LocalTime endTime;
    private ClassRoom classRoom;
    private Course course;

    public Event() { }

    public Event(Integer id, LocalDate dayOfEvent, LocalTime startTime, LocalTime endTime,
                 ClassRoom classRoom, Course course) {
        this.id = id;
        this.dayOfEvent = dayOfEvent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classRoom = classRoom;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDayOfEvent() {
        return dayOfEvent;
    }

    public void setDayOfEvent(LocalDate dayOfEvent) {
        this.dayOfEvent = dayOfEvent;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(dayOfEvent, event.dayOfEvent) &&
                Objects.equals(startTime, event.startTime) &&
                Objects.equals(endTime, event.endTime) &&
                Objects.equals(course, event.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfEvent, startTime, endTime, course);
    }

}
