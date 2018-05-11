package tech.lapsa.insurance.crm.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import tech.lapsa.insurance.crm.validation.constraint.UniqueAgreementNumberConstraintValidator;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueAgreementNumberConstraintValidator.class)
public @interface UniqueAgreementNumber {

    String message() default "{tech.lapsa.insurance.crm.validation.UniqueAgreementNumber.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
