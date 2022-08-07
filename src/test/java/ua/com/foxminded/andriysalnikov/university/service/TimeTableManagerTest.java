package ua.com.foxminded.andriysalnikov.university.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.andriysalnikov.university.exceptions.TimeTableManagerException;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.User;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.EventService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.service.TimeTableManager;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TimeTableManagerTest {

    @Mock
    EventService eventService;
    @Mock
    StudentService studentService;
    @Mock
    TeacherService teacherService;
    @InjectMocks
    TimeTableManager timeTableManager;

    @Test
    void getTimeTableFromStartDateToEndDateByUser_shouldReturnListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainUser() {
        List<Event> events = TestDTOFactory.createListOfEventsConstrainedByStartDateEndDateForTest();
        List<Course> courses = TestDTOFactory.createListOfCoursesForTest();
        lenient().when(studentService.getStudentCoursesByStudentId(anyInt())).thenReturn(courses);
        lenient().when(teacherService.getTeacherCoursesByTeacherId(anyInt())).thenReturn(courses);
        when(eventService.getAllEventsFromStartDateToEndDateByCourseId(
                any(LocalDate.class), any(LocalDate.class), anyInt())).thenReturn(events);
        User user = new Student(1, "First Name", "Last Name");
        TimeTable expectedTimeTable =
                TestDTOFactory.createTimeTableFromStartDateToEndDateByUser();
        TimeTable returnedTimeTable =
                timeTableManager.getTimeTableFromStartDateToEndDateByUser(LocalDate.MIN, LocalDate.MAX, user);
        returnedTimeTable.setEvents(returnedTimeTable.getEvents().stream()
                .sorted(Comparator.comparing(Event::getDayOfEvent)
                        .thenComparing(Event::getStartTime)
                        .thenComparing(Event::getId))
                .collect(Collectors.toList()));
        assertEquals(expectedTimeTable, returnedTimeTable);
        verify(eventService, times(3)).getAllEventsFromStartDateToEndDateByCourseId(
                any(LocalDate.class), any(LocalDate.class), anyInt());
        verify(studentService, atLeast(0)).getStudentCoursesByStudentId(anyInt());
        verify(studentService, atMostOnce()).getStudentCoursesByStudentId(anyInt());
        verify(teacherService, atLeast(0)).getTeacherCoursesByTeacherId(anyInt());
        verify(teacherService, atMostOnce()).getTeacherCoursesByTeacherId(anyInt());
    }

    @Test
    void getTimeTableFromStartDateToEndDateByUser_shouldThrowIllegalArgumentException_whenArgumentsContainStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now();
        User user = new User() {
            @Override
            public void setId(Integer id) {
                super.setId(1);
            }
        };
        assertThrows(TimeTableManagerException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                        startDate, endDate, user));
    }

    @ParameterizedTest
    @MethodSource("provideForOneOrBothDataArgumentsNull")
    void getTimeTableFromStartDateToEndDateByUser_shouldThrowIllegalArgumentException_whenOneOrBothDataArgumentsNull(
            LocalDate startDate, LocalDate endDate) {
        User user = new User() {
            @Override
            public void setId(Integer id) {
                super.setId(id);
            }
        };
        assertThrows(TimeTableManagerException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                        startDate, endDate, user));
    }

    @ParameterizedTest
    @MethodSource("provideForUserOrUserIdIsNull")
    void getTimeTableFromStartDateToEndDateByUser_shouldThrowIllegalArgumentException_whenUserOrUserIdIsNull(
            User user) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        assertThrows(TimeTableManagerException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                        startDate, endDate, user));
    }

    private static Stream<Arguments> provideForOneOrBothDataArgumentsNull() {
        return Stream.of(
                Arguments.of(null, LocalDate.now()),
                Arguments.of(LocalDate.now(), null),
                Arguments.of(null, null)
        );
    }

    private static Stream<Arguments> provideForUserOrUserIdIsNull() {
        return Stream.of(
                Arguments.of((User) null),
                Arguments.of(new User() {
                    @Override
                    public void setFirstName(String firstName) {
                        super.setFirstName(firstName);
                    }
                })
        );
    }

}
