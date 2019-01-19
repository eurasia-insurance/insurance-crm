package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.InsuranceRequestStatus.*;
import static com.lapsa.insurance.elements.ProgressStatus.*;
import static com.lapsa.utils.security.SecurityUtils.*;
import static tech.lapsa.java.commons.function.MyExceptions.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Currency;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.PaymentData;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.crm.validation.UniqueAgreementNumber;
import tech.lapsa.insurance.crm.validation.ValidPolicyAgreementByNumber;
import tech.lapsa.insurance.crm.validation.ValidPolicyNumber;
import tech.lapsa.insurance.facade.InsuranceRequestFacade.InsuranceRequestFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

@Named("policyInvoicePaid")
@RequestScoped
public class PolicyInvoicePaidCDIBean implements ActionCDIBean, Serializable {

    private static final long serialVersionUID = 1L;

    @Named("policyInvoicePaidCheck")
    @Dependent
    public static class PolicyInvoicePaidCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public PolicyInvoicePaidCheckCDIBean() {
	    super(PolicyInvoicePaidCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;

    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.insuranceRequestIn(POLICY_ISSUED)
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

    // payerName

    @NotNullValue(message = "Укажите имя плательщика")
    private String payerName;

    public String getPayerName() {
	return payerName;
    }

    public void setPayerName(String payerName) {
	this.payerName = payerName;
    }

    // payeeTaxpayerNumber

    @NotNullValue(message = "Укажите ИИН плательщика")
    private TaxpayerNumber payeeTaxpayerNumber;

    public TaxpayerNumber getPayeeTaxpayerNumber() {
	return payeeTaxpayerNumber;
    }

    public void setPayeeTaxpayerNumber(TaxpayerNumber payeeTaxpayerNumber) {
	this.payeeTaxpayerNumber = payeeTaxpayerNumber;
    }

    // paidAmount

    @NotNullValue(message = "Укажите сумму оплаченной премии")
    @Min(value = 1, message = "Укажите сумму оплаченной премии")
    private Double paidAmount;

    public Double getPaidAmount() {
	return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
	this.paidAmount = paidAmount;
    }

    // paidInstant

    @NotNullValue(message = "Укажите дату и время оплаты")
    private Instant paidInstant;

    public Instant getPaidInstant() {
	return paidInstant;
    }

    public void setPaidInstant(Instant paidInstant) {
	this.paidInstant = paidInstant;
    }

    // paidReference

    @NotNullValue(message = "Укажите платежный референс")
    private String paidReference;

    public String getPaidReference() {
	return paidReference;
    }

    public void setPaidReference(String paidReference) {
	this.paidReference = paidReference;
    }

    // paidCurrency

    @NotNullValue(message = "Укажите валюту платежа")
    private Currency paidCurrency;

    public Currency getPaidCurrency() {
	return paidCurrency;
    }

    public void setPaidCurrency(Currency paidCurrency) {
	this.paidCurrency = paidCurrency;
    }

    //

    @PostConstruct
    public void init() {
	final InsuranceRequest ir = rrs.getSingleRow().getEntity();

	paidAmount = MyOptionals.of(ir)
		.map(InsuranceRequest::getPayment)
		.map(PaymentData::getInvoiceAmount)
		.orElseThrow(supplier(FacesException::new, "Invoice payment is null"));

	paidCurrency = MyOptionals.of(ir)
		.map(InsuranceRequest::getPayment)
		.map(PaymentData::getInvoiceCurrency)
		.orElseThrow(supplier(FacesException::new, "Invoice currency is null"));

	paidInstant = Instant.now();

	paidReference = null;

	payerName = MyOptionals.of(ir)
		.map(InsuranceRequest::getPayment)
		.map(PaymentData::getInvoicePayeeName)
		.orElseThrow(supplier(FacesException::new, "Invoice payee name is null"));

	payeeTaxpayerNumber = MyOptionals.of(ir)
		.map(InsuranceRequest::getPayment)
		.map(PaymentData::getInvoicePayeeTaxpayerNumber)
		.orElseThrow(supplier(FacesException::new, "Invoice taxpayer number is null"));
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

    @Override
    public String doAction() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for action");

	final InsuranceRequest ir1 = rrs.getSingleRow().getEntity();

	try {
	    final InsuranceRequest ir2 = insuranceRequests.invoicePaidByUs(ir1,
		    "Введено вручную",
		    paidInstant,
		    paidAmount,
		    paidCurrency,
		    null,
		    null,
		    null,
		    payerName,
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
