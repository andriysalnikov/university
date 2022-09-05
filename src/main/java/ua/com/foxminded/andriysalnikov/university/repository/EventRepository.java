package ua.com.foxminded.andriysalnikov.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(DBConstants.JPQL_GET_ALL_EVENTS)
    List<Event> getAllEvents();

    @Query(DBConstants.JPQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID)
    List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer courseId);

    Integer deleteEventById(Integer id);

    Optional<Event> findEventById(Integer id);

}