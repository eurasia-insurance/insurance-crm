package tech.lapsa.insurance.crm.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import tech.lapsa.insurance.crm.validation.constraint.ValidPolicyAgreementNumberConstraintValidator;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidPolicyAgreementNumberConstraintValidator.class)
public @interface ValidPolicyAgreementNumber {

    String message() default "Договор не найден";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
