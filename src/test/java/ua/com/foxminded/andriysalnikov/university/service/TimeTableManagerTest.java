package ua.com.foxminded.andriysalnikov.university.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TimeTableManagerTest {

    @Mock
    EventService eventService;
    @InjectMocks
    TimeTableManager timeTableManager;

    @Test
    void getTimeTableFromStartDateToEndDateByStudent_shouldReturnListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainStudent() {
        List<Event> events = TestDTOFactory.createListOfEventsConstrainedByStartDateEndDateForTest();
        Student student = TestDTOFactory.createStudentWithCoursesForTest();
        System.out.println(student.getCourses());
        when(eventService.getAllEventsFromStartDateToEndDateByCourseId(
                any(LocalDate.class), any(LocalDate.class), anyInt())).thenReturn(events);
        TimeTable expectedTimeTable =
                TestDTOFactory.createTimeTableFromStartDateToEndDate();
        TimeTable returnedTimeTable =
                timeTableManager.getTimeTableFromStartDateToEndDateByStudent(LocalDate.now(), LocalDate.now(), student);
        assertEquals(expectedTimeTable, returnedTimeTable);
        verify(eventService, times(3)).getAllEventsFromStartDateToEndDateByCourseId(
                any(LocalDate.class), any(LocalDate.class), anyInt());
    }

    @Test
    void getTimeTableFromStartDateToEndDateByStudent_shouldThrowIllegalArgumentException_whenArgumentsContainStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now();
        Student student = new Student("Oloo", "Trololo");
        assertThrows(ServiceException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByStudent(
                        startDate, endDate, student));
    }

    @ParameterizedTest
    @MethodSource("provideForOneOrBothDataArgumentsNull")
    void getTimeTableFromStartDateToEndDateByUser_shouldThrowIllegalArgumentException_whenOneOrBothDataArgumentsNull(
            LocalDate startDate, LocalDate endDate) {
        Student student = new Student("Oloo", "Trololo");
        assertThrows(ServiceException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByStudent(
                        startDate, endDate, student));
    }

    @ParameterizedTest
    @MethodSource("provideForStudentOrStudentParametersAreNull")
    void getTimeTableFromStartDateToEndDateByStudent_shouldThrowIllegalArgumentException_whenStudentOrStudentParametersAreNull(
            Student student) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        assertThrows(ServiceException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByStudent(
                        startDate, endDate, student));
    }

    @ParameterizedTest
    @MethodSource("provideForTeacherOrTeacherParametersAreNull")
    void getTimeTableFromStartDateToEndDateByTeacher_shouldThrowIllegalArgumentException_whenTeacherOrTeacherParametersAreNull(
            Teacher teacher) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        assertThrows(ServiceException.class,
                () -> timeTableManager.getTimeTableFromStartDateToEndDateByTeacher(
                        startDate, endDate, teacher));
    }

    private static Stream<Arguments> provideForOneOrBothDataArgumentsNull() {
        return Stream.of(
                Arguments.of(null, LocalDate.now()),
                Arguments.of(LocalDate.now(), null),
                Arguments.of(null, null)
        );
    }

    private static Stream<Arguments> provideForStudentOrStudentParametersAreNull() {
        Student student1 = new Student("First Name", null);
        student1.setId(1);
        Student student2 = new Student();
        student2.setLastName("Last Name");
        student2.setId(2);
        return Stream.of(
                Arguments.of((Student) null),
                Arguments.of(student1),
                Arguments.of(student2));
    }

    private static Stream<Arguments> provideForTeacherOrTeacherParametersAreNull() {
        Teacher teacher1 = new Teacher("First Name", null);
        teacher1.setId(1);
        Teacher teacher2 = new Teacher(null, "Last Name");
        teacher2.setId(2);
        return Stream.of(
                Arguments.of((Teacher) null),
                Arguments.of(teacher1),
                Arguments.of(teacher2));
    }

}
