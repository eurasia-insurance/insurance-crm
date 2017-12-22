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
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyObjects;
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

    @Inject
    private CurrentUserHolder currentUser;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    // epayment-facade (remote)

    @EJB
    private EpaymentFacadeRemote epayments;

    public String doComplete() throws FacesException, IllegalStateException, IllegalArgumentException {

	final Request r = requestHolder.getValue().getEntity();

	MyObjects.requireNonNull(r, "request");
	if (r.getProgressStatus() == ProgressStatus.FINISHED)
	    throw MyExceptions.format(IllegalStateException::new, "Progress status is invalid %1$s",
		    r.getProgressStatus());

	final Instant now = Instant.now();

	r.setUpdated(now);
	r.setCompleted(now);
	r.setCompletedBy(currentUser.getValue());
	r.setNote(note);
	r.setProgressStatus(ProgressStatus.FINISHED);

	if (MyObjects.isA(r, InsuranceRequest.class)) {
	    final InsuranceRequest ir = MyObjects.requireA(r, InsuranceRequest.class);
	    if (ir.getPayment().getStatus() == PaymentStatus.DONE)
		throw MyExceptions.format(IllegalStateException::new, "Request already paid");
	    ir.setTransactionStatus(TransactionStatus.NOT_COMPLETED);
	    ir.getPayment().setStatus(PaymentStatus.CANCELED);
	    ir.setTransactionProblem(problem);
	    ir.setAgreementNumber(null);
	}

	final Request saved;
	try {
	    saved = requestDAO.save(r);
	} catch (IllegalArgument e) {
	    // it should not happen
	    throw new FacesException(e);
	}

	if (MyObjects.isA(r, InsuranceRequest.class) && paidable) {
	    final InsuranceRequest ir = MyObjects.requireA(saved, InsuranceRequest.class);
	    final String invoiceNumber = ir.getPayment().getInvoiceNumber();
	    try {
		epayments.expireInvoice(invoiceNumber);
	    } catch (IllegalArgument | IllegalState | InvoiceNotFound e) {
		// it should not happen
		throw new FacesException(e);
	    }
	}

	requestHolder.setValue(RequestRow.from(r));

	return null;
    }
}
