package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.TransactionProblem;

import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.RequestCompletionFacade.RequestCompletionFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyStrings;
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

    // wasPaidBefore

    private boolean wasPaidBefore;

    public boolean isWasPaidBefore() {
	return wasPaidBefore;
    }

    // problem

    @NotNullValue(message = "Укажите причину")
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

    @FacesValidator("transactionUncomplete.noteValidator")
    public static class NoteValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    final TransactionProblem problem = (TransactionProblem) ((UIInput) component.getAttributes()
		    .get("problemComp")).getValue();
	    if (problem == TransactionProblem.OTHER && (value == null || MyStrings.empty(value.toString())))
		throw new ValidatorException(Messages.createError("Опишите причину в разделе примечание"));
	}
    }

    // controls

    @PostConstruct
    public void init() { // default values
	final RequestRow<?> rr = requestHolder.getValue();
	if (rr != null) {
	    this.paidable = rr.getPayment() != null;
	    this.wasPaidBefore = paidable && rr.getPaymentInstant() != null;
	}
    }

    // CDIs

    // local

    @Inject
    private RequestHolder requestHolder;

    @Inject
    private CurrentUserHolder currentUser;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private RequestCompletionFacadeRemote completions;

    public String doComplete() throws FacesException, IllegalStateException, IllegalArgumentException {

	final Request r = requestHolder.getValue().getEntity();

	final Request result;
	try {
	    result = completions.transactionUncomplete(r, currentUser.getValue(), note, problem, paidable);
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	}

	requestHolder.setValue(RequestRow.from(result));

	return null;
    }
}
