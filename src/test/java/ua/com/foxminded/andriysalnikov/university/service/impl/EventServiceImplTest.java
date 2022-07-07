package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    EventDAO eventDAO;
    @InjectMocks
    EventServiceImpl eventServiceImpl;

    @Test
    void getAllEvents_shouldReturnListOfAllEvents() {
        List<Event> expectedEvents = TestDTOFactory.createListOfAllEventsForTest();
        when(eventDAO.getAllEvents()).thenReturn(expectedEvents);
        List<Event> returnedEvents = eventServiceImpl.getAllEvents();
        assertSame(expectedEvents, returnedEvents);
        verify(eventDAO, times(1)).getAllEvents();
    }

    @Test
    void getAllEventsFromStartDateToEndDateByCourseId_shouldReturnListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainInteger() {
        List<Event> expectedEvents
                = TestDTOFactory.createListOfEventsConstrainedByStartDateEndDateForTest();
        when(eventDAO.getAllEventsFromStartDateToEndDateByCourseId(LocalDate.MIN, LocalDate.MAX, 5))
                .thenReturn(expectedEvents);
        List<Event> returnedEvents = eventServiceImpl
                .getAllEventsFromStartDateToEndDateByCourseId(LocalDate.MIN, LocalDate.MAX, 5);
        assertSame(expectedEvents, returnedEvents);
        verify(eventDAO, times(1))
                .getAllEventsFromStartDateToEndDateByCourseId(
                        any(LocalDate.class), any(LocalDate.class), any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getAllEventsFromStartDateToEndDateByCourseId_shouldThrowIllegalArgumentException_whenArgumentsContainIntegerNull(
            Integer id) {
        assertThrows(IllegalArgumentException.class,
                () -> eventServiceImpl.getAllEventsFromStartDateToEndDateByCourseId(
                        LocalDate.MIN, LocalDate.MAX, id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-6", "-9"})
    void getAllEventsFromStartDateToEndDateByCourseId_shouldThrowIllegalArgumentException_whenArgumentsContainIntegerLessOrEqualsZero(
            Integer id) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        assertThrows(IllegalArgumentException.class,
                () -> eventServiceImpl.getAllEventsFromStartDateToEndDateByCourseId(
                        startDate, endDate, id));
    }

    @Test
    void getAllEventsFromStartDateToEndDateByCourseId_shouldThrowIllegalArgumentException_whenArgumentsContainStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now();
        assertThrows(IllegalArgumentException.class,
                () -> eventServiceImpl.getAllEventsFromStartDateToEndDateByCourseId(
                        startDate, endDate, 1));
    }

    @ParameterizedTest
    @MethodSource("provideForOneOrBothDataArgumentsNull")
    void getAllEventsFromStartDateToEndDateByCourseId_shouldThrowIllegalArgumentException_whenOneOrBothDataArgumentsNull(
            LocalDate startDate, LocalDate endDate) {
        assertThrows(IllegalArgumentException.class,
                () -> eventServiceImpl.getAllEventsFromStartDateToEndDateByCourseId(
                        startDate, endDate, 1));
    }

    private static Stream<Arguments> provideForOneOrBothDataArgumentsNull() {
        return Stream.of(
                Arguments.of(null, LocalDate.now()),
                Arguments.of(LocalDate.now(), null),
                Arguments.of(null, null)
        );
    }

}