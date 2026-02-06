package com.grandmasfood.v1.config.customBeans;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
@Pattern(
        regexp = "^(CC|TI|CE|P)-\\d{1,17}$"
)
public @interface Document {

    String message() default "Invalid document format. Must be like expected example: CC-123456789";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
