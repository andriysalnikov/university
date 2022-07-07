package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherDAO teacherDAO;
    @InjectMocks
    private TeacherServiceImpl teacherServiceImpl;

    @Test
    void getTeacherById_shouldReturnTeacher_whenArgumentIsInteger() {
        Teacher teacher = new Teacher(1, "Thor", "Heyerdahl");
        when(teacherDAO.getTeacherById(1)).thenReturn(teacher);
        assertSame(teacher, teacherServiceImpl.getTeacherById(1));
        verify(teacherDAO, times(1)).getTeacherById(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getTeacherById_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(IllegalArgumentException.class,
                () -> teacherServiceImpl.getTeacherById(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-3", "-12"})
    void getTeacherById_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(IllegalArgumentException.class,
                () -> teacherServiceImpl.getTeacherById(id));
    }

    @Test
    void getTeacherCoursesByTeacherId_shouldReturnListOfTeacherCourses_whenArgumentIsInteger() {
        List<Course> expectedCourses = TestDTOFactory.createListOfCoursesForTest();
        when(teacherDAO.getTeacherCoursesByTeacherId(1)).thenReturn(expectedCourses);
        List<Course> returnedCourses = teacherServiceImpl.getTeacherCoursesByTeacherId(1);
        assertSame(expectedCourses, returnedCourses);
        verify(teacherDAO, times(1)).getTeacherCoursesByTeacherId(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getTeacherCoursesByTeacherId_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(IllegalArgumentException.class,
                () -> teacherServiceImpl.getTeacherCoursesByTeacherId(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-5", "-27"})
    void getTeacherCoursesByTeacherId_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(IllegalArgumentException.class,
                () -> teacherServiceImpl.getTeacherCoursesByTeacherId(id));
    }

}