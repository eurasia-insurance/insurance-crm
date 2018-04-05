package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
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

import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.TransactionProblem;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.RequestCompletionFacade.RequestCompletionFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.javax.validation.NotNullValue;

@Named("transactionUncomplete")
@RequestScoped
public class TransactionUncompleteCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("transactionUncompleteCheck")
    @Dependent
    public static class TransactionUncompleteCheckCDIBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// list

	private List<RequestRow<?>> list;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// CDIs

	// local

	@Inject
	private RequestHolder requestHolder;

	// controls

	@PostConstruct
	public void init() {
	    list = MyCollections.orEmptyList(requestHolder.getValue());
	    allowed = isInRole(InsuranceRoleGroup.CHANGERS)
		    && !list.isEmpty() //
		    && list.stream() //
			    .allMatch(r -> !r.getProgressStatus().equals(ProgressStatus.FINISHED)
				    && !r.getPaymentStatus().equals(PaymentStatus.DONE)) //
	    ;
	}

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

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private TransactionUncompleteCheckCDIBean check;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private RequestCompletionFacadeRemote completions;

    public String doUncomplete() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	if (!check.isAllowed())
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	check.list.stream() //
		.forEach(rr -> {
		    try {
			final boolean paidable = rr.getPayment() != null;
			completions.transactionUncomplete(rr.getEntity(), currentUser.getValue(), note, problem,
				paidable);
		    } catch (IllegalState e) {
			throw new FacesException(e.getRuntime());
		    } catch (IllegalArgument e) {
			throw new FacesException(e.getRuntime());
		    }
		});
	return null;
    }
}
