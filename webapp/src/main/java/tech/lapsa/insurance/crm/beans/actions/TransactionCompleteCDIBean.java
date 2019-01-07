package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.policy.Policy;

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
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;

@Named("transactionComplete")
@RequestScoped
public class TransactionCompleteCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("transactionCompleteCheck")
    @Dependent
    public static class TransactionCompleteCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public TransactionCompleteCheckCDIBean() {
	    super(TransactionCompleteCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;

    }

    static boolean checkActionAllowed(RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS) //
		&& rrs != null
		&& rrs.isSingleSelected() //
		&& rrs.getSingleRow().isCanComplete() //
	;
    }

    // paidable

    private boolean paidable = false;

    public boolean isPaidable() {
	return paidable;
    }

    // wasPaidBefore

    private boolean wasPaidBefore;

    public boolean isWasPaidBefore() {
	return wasPaidBefore;
    }

    // paidAmount

    @NotNullValue(message = "Укажите сумму оплаченной премии")
    @Min(value = 1, message = "Укажите сумму оплаченной премии")
    private Double paidAmount;

    public Double getPaidAmount() {
	return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
	if (wasPaidBefore)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidAmount = paidAmount;
    }

    // paidInstant

    @NotNullValue(message = "Укажите дату и время оплаты")
    private Instant paidInstant;

    public Instant getPaidInstant() {
	return paidInstant;
    }

    public void setPaidInstant(Instant paidInstant) {
	if (wasPaidBefore)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidInstant = paidInstant;
    }

    // paidReference

    @NotNullValue(message = "Укажите платежный референс")
    private String paidReference;

    public String getPaidReference() {
	return paidReference;
    }

    public void setPaidReference(String paidReference) {
	if (wasPaidBefore)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidReference = paidReference;
    }

    // paidCurrency

    @NotNullValue(message = "Укажите валюту платежа")
    private Currency paidCurrency;

    public Currency getPaidCurrency() {
	return paidCurrency;
    }

    public void setPaidCurrency(Currency paidCurrency) {
	if (wasPaidBefore)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidCurrency = paidCurrency;
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

    @EJB
    private PolicyFacadeRemote policies;

    public void agreementNumberChanged(ValueChangeEvent event) {
	try {
	    final Policy fetchedPolicy = policies.getByNumber((String) event.getNewValue());
	    fetchedInsurantName = fetchedPolicy.getInsurant().getPersonal().getFullName();
	    fetchedPolicyAmount = fetchedPolicy.getActual().getAmount();
	    fetchedPolicyDate = fetchedPolicy.getDateOfIssue();
	} catch (PolicyNotFound e) {
	    throw new FacesException(e);
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}
    }

    // fetchedInsurantName

    private String fetchedInsurantName;

    public String getFetchedInsurantName() {
	return fetchedInsurantName;
    }

    // fetchedPolicyAmount

    private Double fetchedPolicyAmount;

    public Double getFetchedPolicyAmount() {
	return fetchedPolicyAmount;
    }

    // fetchedPolicyDate

    private LocalDate fetchedPolicyDate;

    public LocalDate getFetchedPolicyDate() {
	return fetchedPolicyDate;
    }

    // payerName

    private String payerName;

    public String getPayerName() {
	return payerName;
    }

    public void setPayerName(String payerName) {
	this.payerName = payerName;
    }

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private RequestsSelectionCDIBean rrs;

    // insurance-facade (remote)

    @EJB
    private InsuranceRequestFacadeRemote insuranceRequests;

    public String doComplete() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	final InsuranceRequest r1 = rrs.getSingleRow().getEntity();

	try {
	    final Request res;
	    if (paidable && !wasPaidBefore)
		res = insuranceRequests.policyIssuedAndPremiumPaid(r1,
			currentUser.getValue(),
			agreementNumber,
			"Введено вручную",
			paidAmount,
			paidCurrency,
			paidInstant,
			paidReference,
			payerName);
	    else
		res = insuranceRequests.policyIssued(r1, currentUser.getValue(), agreementNumber);
	    rrs.setSingleRow(RequestRow.from(res));
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	} finally {
	    // rrs.reset();
	}
	return null;
    }

    @PostConstruct
    public void init() { // default values
	final RequestRow<?> rr = rrs.getSingleRow();
	if (rr != null) {
	    this.paidable = rr.getPayment() != null;
	    this.wasPaidBefore = paidable && rr.getPaymentInstant() != null;
	    this.payerName = rr.getRequesterName();
	    if (wasPaidBefore) {
		this.paidInstant = rr.getPaymentInstant();
		this.paidAmount = rr.getPaymentAmount();
		this.paidCurrency = rr.getPaymentCurrency();
	    } else {
		this.paidInstant = Instant.now();
		this.paidAmount = rr.getCalculatedAmount();
		this.paidCurrency = Currency.getInstance("KZT");
	    }
	}
    }
}
