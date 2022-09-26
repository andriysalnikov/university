package ua.com.foxminded.andriysalnikov.university.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "university")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_of_event", nullable = false)
    private LocalDate dayOfEvent;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id")
    private ClassRoom classRoom;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    public Event() { }

    public Event(LocalDate dayOfEvent, LocalTime startTime, LocalTime endTime) {
        this.dayOfEvent = dayOfEvent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(LocalDate dayOfEvent, LocalTime startTime, LocalTime endTime,
                 ClassRoom classRoom, Course course) {
        this(dayOfEvent, startTime, endTime);
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

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", dayOfEvent=" + dayOfEvent +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", classRoom=" + classRoom +
                ", course=" + course +
                '}';
    }

}
