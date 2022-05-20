package ua.com.foxminded.andriysalnikov.university.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Faculty {

    private Integer id;
    private String fullName;
    private List<Course> courses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<ClassRoom> classRooms = new ArrayList<>();

    public Faculty() { }

    public Faculty(Integer id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoom> classRooms) {
        this.classRooms = new ArrayList<>(classRooms);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(fullName, faculty.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

}
