package ua.com.foxminded.andriysalnikov.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.com.foxminded.andriysalnikov.university.example.StudentExampleService;
import ua.com.foxminded.andriysalnikov.university.example.StudentIdAndLastName;
import ua.com.foxminded.andriysalnikov.university.model.Student;

@SpringBootApplication
public class UniversityApplication {

	private static StudentExampleService studentExampleService;

	@Autowired
	public UniversityApplication(StudentExampleService studentExampleService){
		UniversityApplication.studentExampleService = studentExampleService;
	}

	public static void main(String[] args) {

		SpringApplication.run(UniversityApplication.class, args);

		StudentIdAndLastName student = studentExampleService.getStudentById(3);
		System.out.println("Student Id       : " + student.getId());
		System.out.println("Student LastName : " + student.getLastName());

		Student anotherStudent = studentExampleService.getStudentByIdWithQuery(3);
		System.out.println(anotherStudent);

	}

}
