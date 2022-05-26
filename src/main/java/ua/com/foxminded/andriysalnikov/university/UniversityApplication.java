package ua.com.foxminded.andriysalnikov.university;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.andriysalnikov.university.config.SpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.dao.*;
import ua.com.foxminded.andriysalnikov.university.dao.impl.*;
import ua.com.foxminded.andriysalnikov.university.model.*;

import java.util.List;

public class UniversityApplication {

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        // Run the Application. Does it work?

        context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

//        showHowTeacherDAOWorks();
//        showHowFacultyDAOWorks();
//        showHowCourseDAOWorks();
//        showHowClassRoomDAOWorks();
//        showHowStudentDAOWorks();
        showHowEventsDAOWorks();

        context.close();

    }

    private static <T> void printAll(List<T> entities) {
        for (T entity : entities) {
            System.out.println(entity);
        }
    }

    private static void showHowTeacherDAOWorks() {

        TeacherDAO teacherDAO = context.getBean(TeacherDAOImpl.class);
        Teacher teacher;

        printAll(teacherDAO.getAllTeachers());
        System.out.println("---");

        System.out.println(teacherDAO.getTeacherById(3));
        System.out.println("---");

        teacher = teacherDAO.createTeacher(new Teacher(0, "Frank", "Sinatra"));
        if (teacher != null) {
            System.out.println("Teacher " + teacher + " created!");
        } else {
            System.out.println("This teacher is already present");
        }
        printAll(teacherDAO.getAllTeachers());
        System.out.println("---");

        teacher = teacherDAO.deleteTeacherById(2);
        if (teacher != null) {
            System.out.println("Teacher " + teacher + " deleted!");
        } else {
            System.out.println("There is no teacher like this");
        }
        printAll(teacherDAO.getAllTeachers());
        System.out.println("---");

        teacher = teacherDAO.getTeacherById(5);
        System.out.println(teacher);
        teacher.setFirstName("Givi");
        teacher.setLastName("Goridze");
        teacher = teacherDAO.updateTeacher(teacher);
        System.out.println(teacher);
        System.out.println("---");

        printAll(teacherDAO.getAllTeachers());
        System.out.println("-----------------------------------------------------");

    }

    private static void showHowFacultyDAOWorks() {

        FacultyDAO facultyDAO = context.getBean(FacultyDAOImpl.class);
        Faculty faculty;

        printAll(facultyDAO.getAllFaculties());
        System.out.println("---");

        System.out.println(facultyDAO.getFacultyById(3));
        System.out.println("---");

        faculty = facultyDAO.createFaculty(new Faculty(0, "Faculty of Jumping"));
        if (faculty != null) {
            System.out.println("Faculty " + faculty + " created!");
        } else {
            System.out.println("This faculty is already present");
        }
        printAll(facultyDAO.getAllFaculties());
        System.out.println("---");

        faculty = facultyDAO.deleteFacultyById(2);
        if (faculty != null) {
            System.out.println("Faculty " + faculty + " deleted!");
        } else {
            System.out.println("There is no faculty like this");
        }
        printAll(facultyDAO.getAllFaculties());
        System.out.println("---");

        faculty = facultyDAO.getFacultyById(1);
        System.out.println(faculty);
        faculty.setFullName("Faculty of Massage");
        faculty = facultyDAO.updateFaculty(faculty);
        System.out.println(faculty);
        System.out.println("---");

        printAll(facultyDAO.getAllFaculties());
        System.out.println("-----------------------------------------------------");

    }

    private static void showHowCourseDAOWorks() {

        CourseDAO courseDAO = context.getBean(CourseDAOImpl.class);
        Course course;

        printAll(courseDAO.getAllCourses());
        System.out.println("---");

        System.out.println(courseDAO.getCourseById(2));
        System.out.println("---");

        course = courseDAO.createCourse(new Course(0, "Litroball", "you don't need to know about it"));
        if (course != null) {
            System.out.println("Course " + course + " created!");
        } else {
            System.out.println("This course is already present");
        }
        printAll(courseDAO.getAllCourses());
        System.out.println("---");

        course = courseDAO.deleteCourserById(3);
        if (course != null) {
            System.out.println("Course " + course + " deleted!");
        } else {
            System.out.println("There is no course like this");
        }
        printAll(courseDAO.getAllCourses());
        System.out.println("---");

        course = courseDAO.getCourseById(5);
        System.out.println(course);
        course.setDescription("Need to learn it!!!");
        course = courseDAO.updateCourse(course);
        System.out.println(course);
        System.out.println("---");

        printAll(courseDAO.getAllCourses());
        System.out.println("-----------------------------------------------------");

    }

    private static void showHowClassRoomDAOWorks() {

        ClassRoomDAO classRoomDAO = context.getBean(ClassRoomDAOImpl.class);
        ClassRoom classRoom;

        printAll(classRoomDAO.getAllClassRooms());
        System.out.println("---");

        System.out.println(classRoomDAO.getClassRoomById(1));
        System.out.println(classRoomDAO.getClassRoomById(5));
        System.out.println("---");

        classRoom = classRoomDAO.createClassRoom(new ClassRoom(0, "Dining room"));
        if (classRoom != null) {
            System.out.println("Classroom " + classRoom + " created!");
        } else {
            System.out.println("This classroom is already present");
        }
        printAll(classRoomDAO.getAllClassRooms());
        System.out.println("---");

        classRoom = classRoomDAO.deleteClassRoomById(13);
        if (classRoom != null) {
            System.out.println("Classroom " + classRoom + " deleted!");
        } else {
            System.out.println("There is no classroom like this");
        }
        printAll(classRoomDAO.getAllClassRooms());
        System.out.println("---");

        classRoom = classRoomDAO.getClassRoomById(5);
        System.out.println(classRoom);
        classRoom.setName("ABC");
        classRoom = classRoomDAO.updateClassRoom(classRoom);
        System.out.println(classRoom);
        System.out.println("---");

        printAll(classRoomDAO.getAllClassRooms());
        System.out.println("-----------------------------------------------------");

    }

    private static void showHowStudentDAOWorks() {

        StudentDAO studentDAO = context.getBean(StudentDAOImpl.class);
        Student student;

        printAll(studentDAO.getAllStudents());
        System.out.println("---");

        System.out.println(studentDAO.getStudentById(3));
        System.out.println("---");

        student = studentDAO.createStudent(new Student(0, "Frank", "Sinatra"));
        if (student != null) {
            System.out.println("Student " + student + " created!");
        } else {
            System.out.println("This student is already present");
        }
        printAll(studentDAO.getAllStudents());
        System.out.println("---");

        student = studentDAO.deleteStudentById(2);
        if (student != null) {
            System.out.println("Student " + student + " deleted!");
        } else {
            System.out.println("There is no teacher like this");
        }
        printAll(studentDAO.getAllStudents());
        System.out.println("---");

        student = studentDAO.getStudentById(5);
        System.out.println(student);
        student.setFirstName("Givi");
        student.setLastName("Goridze");
        student = studentDAO.updateStudent(student);
        System.out.println(student);
        System.out.println("---");

        printAll(studentDAO.getAllStudents());
        System.out.println("-----------------------------------------------------");

    }

    private static void showHowEventsDAOWorks() {

        EventDAO eventDAO = context.getBean(EventDAOImpl.class);

        printAll(eventDAO.getAllEvents());
        System.out.println("---");

    }

}
