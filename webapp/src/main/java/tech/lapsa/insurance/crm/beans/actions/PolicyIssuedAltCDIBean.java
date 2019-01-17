package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.ProgressStatus.*;
import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.function.Predicate;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.InsurantData;
import com.lapsa.insurance.domain.PersonalData;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.elements.InsuranceRequestStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.crm.validation.UniqueAgreementNumber;
import tech.lapsa.insurance.crm.validation.ValidPolicyAgreementByNumber;
import tech.lapsa.insurance.crm.validation.ValidPolicyNumber;
import tech.lapsa.insurance.facade.InsuranceRequestFacade.InsuranceRequestFacadeRemote;
import tech.lapsa.insurance.facade.PolicyFacade.PolicyFacadeRemote;
import tech.lapsa.insurance.facade.PolicyNotFound;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

@Named("policyIssuedAlt")
@RequestScoped
/**
 * Alternative completion of request
 * 
 * @deprecated to be removed when query below will return empty result set
 * 
 *             <pre>
 * select r.ID, 
 *        r.PROGRESS_STATUS, 
 *       ir.PAYMENT_STATUS, 
 *       ir.AGREEMENT_NUMBER 
 * FROM REQUEST r, 
 *      INSURANCE_REQUEST ir
 * WHERE ir.ID = r.ID 
 *   AND ir.INSURANCE_REQUEST_STATUS = 'PREMIUM_PAID' 
 *   AND r.PROGRESS_STATUS <> 'FINISHED';
 *             </pre>
 */
@Deprecated
public class PolicyIssuedAltCDIBean implements ActionCDIBean, Serializable {

    private static final long serialVersionUID = 1L;

    @Named("policyIssuedAltCheck")
    @Dependent
    public static class PolicyIssuedAltCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public PolicyIssuedAltCheckCDIBean() {
	    super(PolicyIssuedAltCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;

    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.insuranceRequestIn(InsuranceRequestStatus.PREMIUM_PAID)
	    && !rr.progressIn(FINISHED);

    private static boolean checkActionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS)
		&& MyOptionals.of(rrs)
			.filter(RequestsSelectionCDIBean::isSingleSelected)
			.map(RequestsSelectionCDIBean::getSingleRow)
			.filter(ROW_ALLOWED)
			.isPresent();
    }

    // agreementNumber

    @NotNullValue(message = "Укажите номер договора")
    @NotEmptyString(message = "Укажите номер договора")
    @ValidPolicyNumber
    @ValidPolicyAgreementByNumber
    @UniqueAgreementNumber
    private String agreementNumber;

    public String getAgreementNumber() {
	return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
	this.agreementNumber = agreementNumber;
    }

    public void onAgreementNumberChanged(ValueChangeEvent event) {
	fetchPolicyAndUpdateModel((String) event.getNewValue());
    }

    @EJB
    private PolicyFacadeRemote policies;

    private void fetchPolicyAndUpdateModel(String agreementNumber) {
	final Policy fetchedPolicy;
	try {
	    fetchedPolicy = policies.getByNumber(agreementNumber);
	} catch (PolicyNotFound e) {
	    throw new FacesException(e);
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}

	paidAmount = MyOptionals.of(fetchedPolicy)
		.map(Policy::getActual)
		.map(CalculationData::getAmount)
		.orElse(null);

	paidCurrency = MyOptionals.of(fetchedPolicy)
		.map(Policy::getActual)
		.map(CalculationData::getCurrency)
		.orElse(null);

	paidInstant = MyOptionals.of(fetchedPolicy)
		.map(Policy::getPaymentDate)
		.map(it -> it.atStartOfDay(ZoneId.systemDefault()))
		.map(ZonedDateTime::toInstant)
		.orElse(null);

	paidReference = null;

	payerName = MyOptionals.of(fetchedPolicy)
		.map(Policy::getInsurant)
		.map(InsurantData::getPersonal)
		.map(PersonalData::getFullName)
		.orElse(null);

	payeeTaxpayerNumber = MyOptionals.of(fetchedPolicy)
		.map(Policy::getInsurant)
		.map(InsurantData::getIdNumber)
		.orElse(null);

    }

    // payerName

    private String payerName;

    public String getPayerName() {
	return payerName;
    }

    // payeeTaxpayerNumber

    private TaxpayerNumber payeeTaxpayerNumber;

    public TaxpayerNumber getPayeeTaxpayerNumber() {
	return payeeTaxpayerNumber;
    }

    // paidAmount

    private Double paidAmount;

    public Double getPaidAmount() {
	return paidAmount;
    }

    // paidInstant

    private Instant paidInstant;

    public Instant getPaidInstant() {
	return paidInstant;
    }

    // paidReference

    private String paidReference;

    public String getPaidReference() {
	return paidReference;
    }

    // paidCurrency

    private Currency paidCurrency;

    public Currency getPaidCurrency() {
	return paidCurrency;
    }

    //

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private RequestsSelectionCDIBean rrs;

    // insurance-facade (remote)

    @EJB
    private InsuranceRequestFacadeRemote insuranceRequests;

    @Override
    public String doAction() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	final InsuranceRequest ir1 = rrs.getSingleRow().getEntity();

	try {
	    final InsuranceRequest ir2 = insuranceRequests.policyIssuedAlt(ir1, agreementNumber,
		    currentUser.getValue());
	    rrs.setSingleRow(RequestRow.from(ir2));
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	}
	return null;
    }
}
