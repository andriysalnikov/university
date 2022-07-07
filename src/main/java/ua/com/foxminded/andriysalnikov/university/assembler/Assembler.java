package ua.com.foxminded.andriysalnikov.university.assembler;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

public final class Assembler {

    public static ClassRoom assembleClassRoom(SqlRowSet resultSet) {
        ClassRoom classRoom = null;
        if (resultSet.next()) {
            classRoom = new ClassRoom(resultSet.getInt("id"), resultSet.getString("name"));
        }
        return classRoom;
    }

    public static Course assembleCourse(SqlRowSet resultSet) {
        Course course = null;
        if (resultSet.next()) {
            course = new Course(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("description"));
        }
        return course;
    }

    public static Faculty assembleFaculty(SqlRowSet resultSet) {
        Faculty faculty = null;
        if (resultSet.next()) {
            faculty = new Faculty(resultSet.getInt("id"), resultSet.getString("full_name"));
        }
        return faculty;
    }

    public static Student assembleStudent(SqlRowSet resultSet) {
        Student student = null;
        if (resultSet.next()) {
            student = new Student(resultSet.getInt("id"),
                    resultSet.getString("first_name"), resultSet.getString("last_name"));
        }
        return student;
    }

    public static Teacher assembleTeacher(SqlRowSet resultSet) {
        Teacher teacher = null;
        if (resultSet.next()) {
            teacher = new Teacher(resultSet.getInt("id"),
                    resultSet.getString("first_name"), resultSet.getString("last_name"));
        }
        return teacher;
    }

    private Assembler() { }

}
