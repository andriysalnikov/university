package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> getAllEvents() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_EVENTS, Event.class)
                .getResultList();
    }

    @Override
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id) {
        return entityManager
                .createQuery(DBConstants.JPQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID, Event.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("courseId", id)
                .getResultList();
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return Optional.ofNullable(entityManager.find(Event.class, id));
    }

    @Override
    public Optional<Event> createEvent(Event event) {
        entityManager.persist(event);
        return Optional.ofNullable(event);
    }

    @Override
    public Optional<Event> deleteEventById(Integer id) {
        Event event = entityManager.find(Event.class, id);
        entityManager.remove(event);
        return Optional.ofNullable(event);
    }

    @Override
    public Optional<Event> updateEvent(Event event) {
        entityManager.merge(event);
        return Optional.ofNullable(event);
    }

}
