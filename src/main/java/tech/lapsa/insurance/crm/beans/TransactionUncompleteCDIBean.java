package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.time.Instant;

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
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

import tech.lapsa.epayment.facade.EpaymentFacade.EpaymentFacadeRemote;
import tech.lapsa.epayment.facade.InvoiceNotFound;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;

@Named("transactionUncomplete")
@RequestScoped
public class TransactionUncompleteCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // paidable

    private boolean paidable = false;

    public boolean isPaidable() {
	return paidable;
    }

    // problem

    @NotNullValue
    private TransactionProblem problem;

    public TransactionProblem getProblem() {
	return problem;
    }

    public void setProblem(TransactionProblem problem) {
	this.problem = problem;
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
	}
    }

    // CDIs

    // local

    @Inject
    private RequestHolder requestHolder;

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

	ir.setProgressStatus(ProgressStatus.FINISHED);
	ir.setTransactionStatus(TransactionStatus.NOT_COMPLETED);
	ir.getPayment().setStatus(PaymentStatus.CANCELED);
	ir.setTransactionProblem(problem);
	ir.setNote(note);
	ir.setUpdated(Instant.now());

	final InsuranceRequest ir2;
	try {
	    ir2 = requestDAO.save(ir);
	} catch (IllegalArgument e) {
	    // it should not happen
	    throw new FacesException(e);
	}

	if (paidable) {
	    final String invoiceNumber = ir2.getPayment().getInvoiceNumber();
	    try {
		epayments.expireInvoice(invoiceNumber);
	    } catch (IllegalArgument | IllegalState | InvoiceNotFound e) {
		// it should not happen
		throw new FacesException(e);
	    }
	}

	requestHolder.setValue(RequestRow.from(ir2));

	return null;
    }
}
