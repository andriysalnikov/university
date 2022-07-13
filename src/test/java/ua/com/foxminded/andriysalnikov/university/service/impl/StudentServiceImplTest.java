package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentDAO studentDAO;
    @InjectMocks
    StudentServiceImpl studentServiceImpl;

    @Test
    void getStudentById_shouldReturnStudent_whenArgumentIsInteger() {
        Student student = new Student(5, "Elon", "Musk");
        when(studentDAO.getStudentById(5)).thenReturn(Optional.of(student));
        assertSame(student, studentServiceImpl.getStudentById(5));
        verify(studentDAO, times(1)).getStudentById(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getTeacherById_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(ServiceException.class,
                () -> studentServiceImpl.getStudentById(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-6", "-9"})
    void getTeacherById_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(ServiceException.class,
                () -> studentServiceImpl.getStudentById(id));
    }

    @Test
    void getStudentCoursesByStudentId_shouldReturnListOfStudentCourses_whenArgumentIsInteger() {
        List<Course> expectedCourses = TestDTOFactory.createListOfCoursesForTest();
        when(studentDAO.getStudentCoursesByStudentId(1)).thenReturn(expectedCourses);
        List<Course> returnedCourses = studentServiceImpl.getStudentCoursesByStudentId(1);
        assertSame(expectedCourses, returnedCourses);
        verify(studentDAO, times(1)).getStudentCoursesByStudentId(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getTeacherCoursesByTeacherId_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(ServiceException.class,
                () -> studentServiceImpl.getStudentCoursesByStudentId(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-8", "-150"})
    void getTeacherCoursesByTeacherId_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(ServiceException.class,
                () -> studentServiceImpl.getStudentCoursesByStudentId(id));
    }

}