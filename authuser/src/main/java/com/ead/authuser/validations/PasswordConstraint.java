package com.ead.authuser.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default """
            Sua senha deve ter de 6 a 15 caracteres, com no mínimo: uma letra maiúscula, um número e um caractere especial (!, @, #, &).
            """;
    Class <?>[] groups() default {};
    Class <? extends Payload>[] payload() default {};
}
