package com.grandmasfood.v1.config.customBeans;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
@Size(max = 10)
@Pattern(
        regexp = "^\\d{10}$"
)
public @interface PhoneNumber {

    String message() default "The phone number must be in the format: 1234567890. Only 10 number digits.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
