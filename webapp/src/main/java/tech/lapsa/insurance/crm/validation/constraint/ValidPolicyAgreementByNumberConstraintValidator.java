package tech.lapsa.insurance.crm.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import tech.lapsa.insurance.crm.validation.ValidPolicyAgreementByNumber;
import tech.lapsa.insurance.facade.PolicyFacade.PolicyFacadeRemote;
import tech.lapsa.insurance.facade.PolicyNotFound;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.naming.MyNaming;

public class ValidPolicyAgreementByNumberConstraintValidator
	implements ConstraintValidator<ValidPolicyAgreementByNumber, String> {

    @Override
    public void initialize(final ValidPolicyAgreementByNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
	if (value == null)
	    return true;

	final PolicyFacadeRemote policies = MyNaming.lookupEJB(ValidationException::new,
		PolicyFacadeRemote.APPLICATION_NAME,
		PolicyFacadeRemote.MODULE_NAME,
		PolicyFacadeRemote.BEAN_NAME,
		PolicyFacadeRemote.class);
	try {
	    policies.getByNumber(value);
	    return true;
	} catch (PolicyNotFound e) {
	    return false;
	} catch (IllegalArgument e) {
	    // checking for valid number is not the main goal of the validation,
	    // so just return 'true'
	    return true;
	}
    }
}
