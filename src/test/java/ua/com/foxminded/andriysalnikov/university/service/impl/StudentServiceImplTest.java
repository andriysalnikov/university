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
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.repository.StudentRepository;
import ua.com.foxminded.andriysalnikov.university.utils.TestDTOFactory;

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
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Test
    void getStudentById_shouldReturnStudent_whenArgumentIsInteger() {
        Student student = new Student("Elon", "Musk");
        when(studentRepository.getStudentById(5)).thenReturn(Optional.of(student));
        assertSame(student, studentServiceImpl.getStudentById(5));
        verify(studentRepository, times(1)).getStudentById(any(Integer.class));
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
    void getTeacherByIdWithCourses_shouldReturnTeacherWithListOfCourses_whenArgumentIsInteger() {
        Student expectedStudent = TestDTOFactory.createStudentWithCoursesForTest();
        when(studentRepository.getStudentByIdWithCourses(1)).thenReturn(Optional.of(expectedStudent));
        Student returnedStudent = studentServiceImpl.getStudentByIdWithCourses(1);
        assertSame(expectedStudent, returnedStudent);
        verify(studentRepository, times(1)).getStudentByIdWithCourses(any(Integer.class));
    }

    @ParameterizedTest
    @NullSource
    void getStudentByIdWithCourses_shouldThrowIllegalArgumentException_whenArgumentIsNull(Integer id) {
        assertThrows(ServiceException.class,
                () -> studentServiceImpl.getStudentByIdWithCourses(id));
    }

    @ParameterizedTest
    @CsvSource({"0", "-5", "-27"})
    void getStudentByIdWithCourses_shouldThrowIllegalArgumentException_whenArgumentIsIntegerLessOrEqualsZero(Integer id) {
        assertThrows(ServiceException.class,
                () -> studentServiceImpl.getStudentByIdWithCourses(id));
    }

}