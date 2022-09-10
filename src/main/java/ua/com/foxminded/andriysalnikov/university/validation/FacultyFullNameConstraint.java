package ua.com.foxminded.andriysalnikov.university.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FacultyFullNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FacultyFullNameConstraint {
    String message() default "Faculty Full Name should start with 'Faculty'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
