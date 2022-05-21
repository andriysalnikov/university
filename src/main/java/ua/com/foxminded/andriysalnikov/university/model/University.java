package ua.com.foxminded.andriysalnikov.university.model;

import java.util.ArrayList;
import java.util.List;

public class University {

    private List<Faculty> faculties;
    private EducationalDepartment educationalDepartment;

    public University() {
        this.faculties = new ArrayList<>();
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = new ArrayList<>(faculties);
    }

    public EducationalDepartment getEducationalDepartment() {
        return educationalDepartment;
    }

    public void setEducationalDepartment(EducationalDepartment educationalDepartment) {
        this.educationalDepartment = educationalDepartment;
    }

}
