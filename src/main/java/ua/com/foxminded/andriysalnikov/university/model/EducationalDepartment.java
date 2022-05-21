package ua.com.foxminded.andriysalnikov.university.model;

import java.util.ArrayList;
import java.util.List;

public class EducationalDepartment {

    private List<Teacher> teachers;

    public EducationalDepartment() {
        this.teachers = new ArrayList<>();
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = new ArrayList<>(teachers);
    }

}
