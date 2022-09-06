package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.repository.TeacherRepository;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherServiceImpl teacherServiceImpl;

    @Test
    void getTeacherById_shouldReturnTeacher_whenArgumentIsInteger() {
        Teacher teacher = new Teacher("Thor", "Heyerdahl");
        teacher.setId(1);
        when(teacherRepository.getTeacherById(1)).thenReturn(Optional.of(teacher));
        assertSame(teacher, teacherServiceImpl.getTeacherById(1));
        verify(teacherRepository, times(1)).getTeacherById(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getTeacherById_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(ServiceException.class,
                () -> teacherServiceImpl.getTeacherById(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-3", "-12"})
    void getTeacherById_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(ServiceException.class,
                () -> teacherServiceImpl.getTeacherById(id));
    }

    @Test
    void getTeacherByIdWithCourses_shouldReturnTeacherWithListOfCourses_whenArgumentIsInteger() {
        Teacher expectedTeacher = TestDTOFactory.createTeacherWithCoursesForTest();
        when(teacherRepository.getTeacherByIdWithCourses(1)).thenReturn(Optional.of(expectedTeacher));
        Teacher returnedTeacher = teacherServiceImpl.getTeacherByIdWithCourses(1);
        assertSame(expectedTeacher, returnedTeacher);
        verify(teacherRepository, times(1)).getTeacherByIdWithCourses(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getTeacherByIdWithCourses_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(ServiceException.class,
                () -> teacherServiceImpl.getTeacherByIdWithCourses(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-5", "-27"})
    void getTeacherByIdWithCourses_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(ServiceException.class,
                () -> teacherServiceImpl.getTeacherByIdWithCourses(id));
    }

}