package tech.lapsa.insurance.crm.validation.constraint;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tech.lapsa.insurance.crm.validation.ValidPolicyNumber;

public class ValidPolicyNumberConstraintValidator
	implements ConstraintValidator<ValidPolicyNumber, String> {

    @Override
    public void initialize(final ValidPolicyNumber constraintAnnotation) {
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]+$");

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
	if (value == null)
	    return true;

	// 288 3Z9 161 03L
	if (value.length() != 12)
	    return false;

	if (!NUMBER_PATTERN.matcher(value).matches())
	    return false;

	return true;

    }
}
