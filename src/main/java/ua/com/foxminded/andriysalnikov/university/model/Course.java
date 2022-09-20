package ua.com.foxminded.andriysalnikov.university.model;

import com.fasterxml.jackson.annotation.JsonView;
import ua.com.foxminded.andriysalnikov.university.marker.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "courses", schema = "university")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.WithCourses.class, View.WithoutDependencies.class})
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Course Name cannot be blank")
    @Size(max = 20, message = "Course Name length must be no longer than 20 symbols")
    @JsonView({View.WithCourses.class, View.WithoutDependencies.class})
    private String name;

    // Description can be Blank
    @Column(name = "description")
    @Size(max = 100, message = "Course Description length must be no longer than 100 symbols")
    @JsonView({View.WithCourses.class, View.WithoutDependencies.class})
    private String description;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Course() { }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }

}
