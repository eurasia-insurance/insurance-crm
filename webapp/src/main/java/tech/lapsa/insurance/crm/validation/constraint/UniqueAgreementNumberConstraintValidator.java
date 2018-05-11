package tech.lapsa.insurance.crm.validation.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import com.lapsa.insurance.domain.policy.PolicyRequest;

import tech.lapsa.insurance.crm.validation.UniqueAgreementNumber;
import tech.lapsa.insurance.dao.PolicyRequestDAO.PolicyRequestDAORemote;
import tech.lapsa.insurance.dao.RequestFilter;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.naming.MyNaming;

public class UniqueAgreementNumberConstraintValidator
	implements ConstraintValidator<UniqueAgreementNumber, String> {

    @Override
    public void initialize(final UniqueAgreementNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
	if (value == null)
	    return true;

	final PolicyRequestDAORemote prs = MyNaming.lookupEJB(ValidationException::new,
		PolicyRequestDAORemote.APPLICATION_NAME,
		PolicyRequestDAORemote.MODULE_NAME,
		PolicyRequestDAORemote.BEAN_NAME,
		PolicyRequestDAORemote.class);

	final RequestFilter filter = new RequestFilter();
	filter.setAgreementNumberMask(value);
	try {
	    final List<PolicyRequest> found = prs.findByFilter(1, 1, filter);
	    if (!found.isEmpty())
		return false; // non-unique
	    return true;
	} catch (IllegalArgument e) {
	    // checking for valid number is not the main goal of the validation,
	    // so just return 'true'
	    return true;
	}
    }
}
