package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.RequestCompletionFacade.RequestCompletionFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;

@Named("commentRequest")
@RequestScoped
public class CommentRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("commentRequestCheck")
    @Dependent
    public static class CommentRequestCheckCDIBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// signle

	private RequestRow<?> single = null;

	// CDIs

	// local

	@Inject
	private RequestHolder requestHolder;

	@PostConstruct
	public void init() {
	    final List<RequestRow<?>> list = MyCollections.orEmptyList(requestHolder.getValue());
	    allowed = isInRole(InsuranceRoleGroup.CHANGERS) //
		    && list.size() == 1 //
	    ;
	    if (!allowed)
		return;
	    single = list.get(0);
	    allowed = single.isCanComment();
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
    private CommentRequestCheckCDIBean check;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private RequestCompletionFacadeRemote completions;

    public String doComment() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	if (!check.isAllowed())
	    throw MyExceptions.format(FacesException::new, "Commenting is unavailable");

	try {
	    completions.commentRequest(check.single.getEntity(), currentUser.getValue(), message);
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	}

	return null;
    }

    @PostConstruct
    public void init() {
	final String oldNote = check.single.getEntity().getNote();
	this.messages = oldNote == null ? "" : oldNote;
    }
}
