package ua.com.foxminded.andriysalnikov.university.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentDTOWithCourses {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final List<String> courses;

    public StudentDTOWithCourses(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "StudentWithCoursesDTO{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", courses=" + courses +
                '}';
    }

}
