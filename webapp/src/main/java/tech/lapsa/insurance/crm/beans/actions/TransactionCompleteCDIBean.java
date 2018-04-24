package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Currency;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;

import com.lapsa.insurance.domain.Request;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.RequestCompletionFacade.RequestCompletionFacadeRemote;
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
	    extends ASelectingChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// signle

	private RequestRow<?> single = null;

	@PostConstruct
	public void init() {
	    final List<RequestRow<?>> list = getSelected();
	    allowed = isInRole(InsuranceRoleGroup.CHANGERS) //
		    && list.size() == 1 //
	    ;
	    if (!allowed)
		return;
	    single = list.get(0);
	    allowed = single.isCanComplete();
	}
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
    private String agreementNumber;

    public String getAgreementNumber() {
	return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
	this.agreementNumber = agreementNumber;
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
    private TransactionCompleteCheckCDIBean check;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private RequestCompletionFacadeRemote completions;

    public String doComplete() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	if (!check.isAllowed())
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	final Request r = check.single.getEntity();

	try {
	    if (paidable && !wasPaidBefore)
		completions.transactionCompleteWithPayment(r,
			currentUser.getValue(),
			agreementNumber,
			"Введено вручную",
			paidAmount,
			paidCurrency,
			paidInstant,
			paidReference,
			payerName);
	    else
		completions.transactionComplete(r, currentUser.getValue(), agreementNumber);
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	} finally {
	    check.clearSelected();
	}
	return null;
    }

    @PostConstruct
    public void init() { // default values
	final RequestRow<?> rr = check.single;
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
