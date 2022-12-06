package com.epam.spring.testingapp.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NameValidator.class)
public @interface NameConstraint {
    String message()default "Invalid name format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload()default {};
}
