package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

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

@Named("commentRequest")
@RequestScoped
public class CommentRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("commentRequestCheck")
    @Dependent
    public static class CommentRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkActionAllowed() {
	    return isInRole(InsuranceRoleGroup.CHANGERS) //
		    && getList().size() == 1 //
		    && getSignle().isCanComment();
	}
    }

    // message

    @NotNullValue(message = "Введите комментарий")
    @NotEmptyString(message = "Введите комментарий")
    @Size(max = 200, message = "Макс 200 символов")
    private String message;

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    // messages

    private String messages;

    public String getMessages() {
	return messages;
    }

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private CommentRequestCheckCDIBean checker;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private RequestCompletionFacadeRemote completions;

    public String doComment() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	checker.refreshList();

	if (!checker.isAllowed())
	    throw MyExceptions.format(FacesException::new, "Commenting is unavailable");

	try {
	    final Request res = completions.commentRequest(checker.getSignle().getEntity(), currentUser.getValue(),
		    message);
	    checker.updateList(RequestRow.from(res));
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	} finally {
	    // check.clearSelected();
	}

	return null;
    }

    @PostConstruct
    public void init() {
	final String oldNote = checker.getSignle().getEntity().getNote();
	this.messages = oldNote == null ? "" : oldNote;
    }
}
