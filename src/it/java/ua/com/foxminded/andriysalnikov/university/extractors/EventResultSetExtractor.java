package ua.com.foxminded.andriysalnikov.university.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class EventResultSetExtractor implements ResultSetExtractor<Event> {
    @Override
    public Event extractData(ResultSet rs) throws SQLException, DataAccessException {
        Event event = null;
        if (rs.next()) {
            ClassRoom classRoom = new ClassRoom(rs.getString("classroom_name"));
            classRoom.setId(rs.getInt("classroom_id"));
            Course course = new Course(rs.getString("course_name"), rs.getString("description"));
            course.setId(rs.getInt("course_id"));
            event = new Event(
                rs.getObject("date_of_event", LocalDate.class),
                rs.getObject("start_time", LocalTime.class),
                rs.getObject("end_time", LocalTime.class),
                classRoom, course);
            event.setId(rs.getInt("id"));
        }
        return event;
    }

}