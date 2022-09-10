package ua.com.foxminded.andriysalnikov.university.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FacultyFullNameValidator implements ConstraintValidator<FacultyFullNameConstraint, String> {

    @Override
    public boolean isValid(String fullName, ConstraintValidatorContext constraintValidatorContext) {
        return fullName.startsWith("Faculty");
    }

}
