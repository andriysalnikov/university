package ua.com.foxminded.andriysalnikov.university.dto;

import java.util.ArrayList;
import java.util.List;

public class FacultyDTOWithCourses {

    private final String id;
    private final String fullName;
    private final List<String> courses;

    public FacultyDTOWithCourses(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.courses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "FacultyWithCoursesDTO{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", courses=" + courses +
                '}';
    }

}
