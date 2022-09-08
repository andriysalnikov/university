package ua.com.foxminded.andriysalnikov.university.model;

import ua.com.foxminded.andriysalnikov.university.validation.FacultyFullNameConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "faculties", schema = "university")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    @NotBlank(message = "Faculty Full Name cannot be blank")
    @Size(max = 100, message = "Faculty Full Name length must be no longer than 100 symbols")
    @FacultyFullNameConstraint
    private String fullName;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "faculty")
    @OrderBy(value = "id")
    private final List<ClassRoom> classRooms;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "faculty")
    @OrderBy(value = "id")
    private final List<Course> courses;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "faculty")
    @OrderBy(value = "id")
    private final List<Student> students;

    public Faculty() {
        this.classRooms = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public Faculty(String fullName) {
        this();
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

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Student> getStudents() {
        return students;
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
