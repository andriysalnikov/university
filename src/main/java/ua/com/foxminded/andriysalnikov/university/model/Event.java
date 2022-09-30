package ua.com.foxminded.andriysalnikov.university.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "university")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
