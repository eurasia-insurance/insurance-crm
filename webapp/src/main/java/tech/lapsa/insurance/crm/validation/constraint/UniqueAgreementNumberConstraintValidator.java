package tech.lapsa.insurance.crm.validation.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import com.lapsa.insurance.domain.InsuranceRequest;

import tech.lapsa.insurance.crm.validation.UniqueAgreementNumber;
import tech.lapsa.insurance.dao.InsuranceRequestDAO.InsuranceRequestDAORemote;
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

	final InsuranceRequestDAORemote prs = MyNaming.lookupEJB(ValidationException::new,
		InsuranceRequestDAORemote.APPLICATION_NAME,
		InsuranceRequestDAORemote.MODULE_NAME,
		InsuranceRequestDAORemote.BEAN_NAME,
		InsuranceRequestDAORemote.class);

	final RequestFilter filter = new RequestFilter();
	filter.setAgreementNumberMask(value);
	try {
	    final List<InsuranceRequest> found = prs.findByAgreementNumber(0, 1, value);
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
