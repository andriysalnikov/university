package ua.com.foxminded.andriysalnikov.university.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students", schema = "university")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinTable(name = "students_courses", schema = "university",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    @OrderBy
    private final List<Course> courses;

    public Student() { this.courses = new ArrayList<>(); }

    public Student(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addCourseToStudent(Course course) {
        courses.add(course);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }

}
