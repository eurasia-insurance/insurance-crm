package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.elements.TransactionProblem;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.RequestCompletionFacade.RequestCompletionFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.javax.validation.NotNullValue;

@Named("transactionUncomplete")
@RequestScoped
public class TransactionUncompleteCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("transactionUncompleteCheck")
    @Dependent
    public static class TransactionUncompleteCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkActionAllowed() {
	    return isInRole(InsuranceRoleGroup.CHANGERS)
		    && !getList().isEmpty() //
		    && getListStream() //
			    .allMatch(RequestRow::isCanUncomplete) //
	    ;
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

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private TransactionUncompleteCheckCDIBean checker;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private RequestCompletionFacadeRemote completions;

    public String doUncomplete() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	checker.refreshList();

	if (!checker.isAllowed())
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	try {
	    final List<RequestRow<?>> res = checker.getListStream() //
		    .map(r -> {
			try {
			    final boolean paidable = r.getPayment() != null;
			    return completions.transactionUncomplete(r.getEntity(), currentUser.getValue(), problem,
				    paidable);
			} catch (IllegalState e) {
			    throw new FacesException(e.getRuntime());
			} catch (IllegalArgument e) {
			    throw new FacesException(e.getRuntime());
			}
		    })
		    .map(RequestRow::from)
		    .collect(MyCollectors.unmodifiableList());
	    checker.updateList(res);
	} finally {
	    // check.clearSelected();
	}
	return null;
    }
}
