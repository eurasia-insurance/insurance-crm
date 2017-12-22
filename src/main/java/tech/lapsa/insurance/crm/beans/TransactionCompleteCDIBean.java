package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.time.Instant;
import java.util.Currency;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.TransactionStatus;

import tech.lapsa.epayment.facade.EpaymentFacade.EpaymentFacadeRemote;
import tech.lapsa.epayment.facade.InvoiceNotFound;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;

@Named("transactionComplete")
@RequestScoped
public class TransactionCompleteCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // paidable

    private boolean paidable = false;

    public boolean isPaidable() {
	return paidable;
    }

    // paid

    private boolean paid;

    public boolean isPaid() {
	return paid;
    }

    // paidAmount

    @NotNullValue
    private Double paidAmount;

    public Double getPaidAmount() {
	return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
	if (paid)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidAmount = paidAmount;
    }

    // paidInstant

    @NotNullValue
    private Instant paidInstant;

    public Instant getPaidInstant() {
	return paidInstant;
    }

    public void setPaidInstant(Instant paidInstant) {
	if (paid)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidInstant = paidInstant;
    }

    // paidReference

    @NotNullValue
    private String paidReference;

    public String getPaidReference() {
	return paidReference;
    }

    public void setPaidReference(String paidReference) {
	if (paid)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidReference = paidReference;
    }

    // paidCurrency

    @NotNullValue
    private Currency paidCurrency;

    public Currency getPaidCurrency() {
	return paidCurrency;
    }

    public void setPaidCurrency(Currency paidCurrency) {
	if (paid)
	    throw MyExceptions.illegalStateFormat("Already paid");
	this.paidCurrency = paidCurrency;
    }

    // agreementNumber

    @NotNullValue
    @NotEmptyString
    private String agreementNumber;

    public String getAgreementNumber() {
	return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
	this.agreementNumber = agreementNumber;
    }

    // note

    @NotNullValue
    @NotEmptyString
    private String note;

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

    // controls

    @PostConstruct
    public void init() { // default values
	final RequestRow<?> rr = requestHolder.getValue();
	if (rr != null) {
	    this.paidable = rr.getPayment() != null;
	    this.paid = paidable && rr.getPaymentInstant() != null;

	    if (paid) {
		this.paidInstant = rr.getPaymentInstant();
		this.paidAmount = rr.getPaymentAmount();
		this.paidCurrency = rr.getPaymentCurrency();
	    } else {
		this.paidInstant = Instant.now();
		this.paidAmount = requestHolder.getValue().getCalculatedAmount();
		this.paidCurrency = Currency.getInstance("KZT");
	    }
	}
    }

    // CDIs

    // local

    @Inject
    private RequestHolder requestHolder;

    @Inject
    private CurrentUserHolder currentUser;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    // epayment-facade (remote)

    @EJB
    private EpaymentFacadeRemote epayments;

    public String doComplete() {

	// TODO check progress status
	// TODO check transaction status

	final Request r = requestHolder.getValue().getEntity();
	final InsuranceRequest ir;
	try {
	    ir = MyObjects.requireA(r, InsuranceRequest.class);
	} catch (IllegalArgumentException e) {
	    // it should not happen
	    throw new FacesException(e);
	}

	final Instant now = Instant.now();

	ir.setUpdated(now);
	ir.setCompleted(now);
	ir.setCompletedBy(currentUser.getValue());

	ir.setProgressStatus(ProgressStatus.FINISHED);
	ir.setTransactionStatus(TransactionStatus.COMPLETED);
	ir.getPayment().setStatus(PaymentStatus.DONE);
	ir.setAgreementNumber(agreementNumber);
	ir.setNote(note);

	final InsuranceRequest ir2;
	try {
	    ir2 = requestDAO.save(ir);
	} catch (IllegalArgument e) {
	    // it should not happen
	    throw new FacesException(e);
	}

	if (paidable && !paid) {
	    final String invoiceNumber = ir2.getPayment().getInvoiceNumber();
	    try {
		epayments.completeWithUnknownPayment(invoiceNumber, paidAmount, paidCurrency, paidInstant,
			paidReference);
	    } catch (IllegalArgument | IllegalState | InvoiceNotFound e) {
		// it should not happen
		throw new FacesException(e);
	    }
	}

	requestHolder.setValue(RequestRow.from(ir2));

	return null;
    }
}
