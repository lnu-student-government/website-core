package org.sglnu.userservice.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.sglnu.userservice.validator.FacultyValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FacultyValidator.class)
public @interface ValidFaculty {
    String message() default "Faculty name is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
