package org.sglnu.userservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sglnu.userservice.common.Faculty;
import org.sglnu.userservice.validator.annotation.ValidFaculty;


public class FacultyValidator implements ConstraintValidator<ValidFaculty, String> {
    @Override
    public void initialize(ValidFaculty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String facultyName, ConstraintValidatorContext context) {
        if (facultyName == null) {
            return false;
        }
        for (Faculty faculty : Faculty.values()) {
            if (faculty.getName().equals(facultyName)) {
                return true;
            }
        }
        return false;
    }
}
