package ua.com.foxminded.andriysalnikov.university.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Faculty {

    private Integer id;
    private String fullName;
    private List<Course> courses;
    private List<Student> students;
    private List<ClassRoom> classRooms;

    public Faculty() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
        this.classRooms = new ArrayList<>();
    }

    public Faculty(Integer id, String fullName) {
        this();
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

    @Override
    public String toString() {
        return "Faculty{" + "id=" + id + ", fullName='" + fullName + '\'' + '}';
    }

}
