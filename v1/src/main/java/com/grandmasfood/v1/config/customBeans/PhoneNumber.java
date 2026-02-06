package com.grandmasfood.v1.config.customBeans;

import jakarta.validation.Constraint;
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
}
