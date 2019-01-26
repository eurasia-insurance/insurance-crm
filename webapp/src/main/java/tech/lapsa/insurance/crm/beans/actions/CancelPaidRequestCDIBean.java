package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.InsuranceRequestStatus.PREMIUM_PAID;
import static com.lapsa.utils.security.SecurityUtils.checkRoleGranted;
import static com.lapsa.utils.security.SecurityUtils.isInRole;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.elements.InsuranceRequestCancellationReason;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.InsuranceRequestFacade.InsuranceRequestFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;

@Named("cancelPaidRequest")
@RequestScoped
public class CancelPaidRequestCDIBean implements ActionCDIBean, Serializable {

    private static final long serialVersionUID = 1L;

    @Named("cancelPaidRequestCheck")
    @Dependent
    public static class CancelPaidRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public CancelPaidRequestCheckCDIBean() {
	    super(CancelPaidRequestCDIBean::checkActionAllowed);
	}
    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.insuranceRequestIn(PREMIUM_PAID);

    private static boolean checkActionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.DELETERS)
		&& MyOptionals.of(rrs)
			.filter(RequestsSelectionCDIBean::isSingleSelected)
			.map(RequestsSelectionCDIBean::getSingleRow)
			.filter(ROW_ALLOWED)
			.isPresent();
    }

    // reason

    @NotNullValue(message = "Укажите причину")
    private InsuranceRequestCancellationReason reason;

    public InsuranceRequestCancellationReason getReason() {
	return reason;
    }

    public void setReason(InsuranceRequestCancellationReason reason) {
	this.reason = reason;
    }

    // comment

    @NotNullValue(message = "Укажите дополнительный комментарий")
    @NotEmptyString(message = "Укажите дополнительный комментарий")
    private String comments;

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
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

    public String doAction() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.DELETERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for canceling payment");

	try {
	    final List<RequestRow<?>> res = rrs.getValueAsStream() //
		    .map(r -> {
			try {
			    return insuranceRequests.paymentCanceled(r.getEntity(), currentUser.getValue(), reason, comments);
			} catch (IllegalState e) {
			    throw new FacesException(e.getRuntime());
			} catch (IllegalArgument e) {
			    throw new FacesException(e.getRuntime());
			}
		    })
		    .map(RequestRow::from)
		    .collect(MyCollectors.unmodifiableList());
	    rrs.setValue(res);
	} finally {
	    // rrs.reset();
	}
	return null;
    }
}
