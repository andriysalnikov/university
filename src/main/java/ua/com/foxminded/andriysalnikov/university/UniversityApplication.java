package ua.com.foxminded.andriysalnikov.university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.andriysalnikov.university.config.SpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.controller.TimeTableManager;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.exceptions.TimeTableManagerException;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.service.impl.StudentServiceImpl;
import ua.com.foxminded.andriysalnikov.university.service.impl.TeacherServiceImpl;

import java.time.LocalDate;

public class UniversityApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityApplication.class);

    public static void main(String[] args) {

        LOGGER.info(Messages.APPLICATION_STARTED);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

        StudentService studentService = context.getBean(StudentServiceImpl.class);
        TeacherService teacherService = context.getBean(TeacherServiceImpl.class);
        TimeTableManager timeTableManager = context.getBean(TimeTableManager.class);


        int id = 1;
        Student student = null;
        LOGGER.info(Messages.TRY_GET_ENTITY_BY_ID, Student.class.getSimpleName(), id);
        try {
            student = studentService.getStudentById(id);
            LOGGER.info(Messages.OK_GET_ENTITY_BY_ID, Student.class.getSimpleName(), id, student);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }

        TimeTable studentTimeTable;
        LocalDate startDate = LocalDate.of(2022, 5, 30);
        LocalDate endDate = LocalDate.of(2022, 6, 3);
        LOGGER.debug(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                startDate, endDate, student);
        try {
            studentTimeTable = timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                    startDate, endDate, student);
            LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                    startDate, endDate, student, studentTimeTable);
        } catch (TimeTableManagerException timeTableManagerException) {
            LOGGER.error(timeTableManagerException.getMessage());
        }


        Teacher teacher = null;
        LOGGER.info(Messages.TRY_GET_ENTITY_BY_ID, Teacher.class.getSimpleName(), id);
        try {
            teacher = teacherService.getTeacherById(id);
            LOGGER.info(Messages.OK_GET_ENTITY_BY_ID, Teacher.class.getSimpleName(), id, teacher);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }

        TimeTable teacherTimeTable;
        startDate = LocalDate.of(2022, 5, 30);
        endDate = LocalDate.of(2022, 6, 3);
        LOGGER.debug(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                startDate, endDate, teacher);
        try {
            teacherTimeTable = timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                    startDate, endDate, teacher);
            LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                    startDate, endDate, student, teacherTimeTable);
        } catch (TimeTableManagerException timeTableManagerException) {
            LOGGER.error(timeTableManagerException.getMessage());
        }

        context.close();
        LOGGER.info(Messages.APPLICATION_FINISHED);

    }

}
