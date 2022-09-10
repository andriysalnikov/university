package ua.com.foxminded.andriysalnikov.university.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "classrooms", schema = "university")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Classroom Name cannot be blank")
    @Size(max = 5, message = "Classroom Name length must be no longer than 5 symbols")
    private String name;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public ClassRoom() { }

    public ClassRoom(String name) {
        this.name = name;
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
        ClassRoom classRoom = (ClassRoom) o;
        return Objects.equals(name, classRoom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ClassRoom{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

}
