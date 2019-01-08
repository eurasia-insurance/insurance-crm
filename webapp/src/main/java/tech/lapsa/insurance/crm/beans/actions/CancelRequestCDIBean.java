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
import tech.lapsa.javax.validation.NotNullValue;

@Named("cancelRequest")
@RequestScoped
public class CancelRequestCDIBean implements ActionCDIBean, Serializable {

    private static final long serialVersionUID = 1L;

    @Named("cancelRequestCheck")
    @Dependent
    public static class CancelRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public CancelRequestCheckCDIBean() {
	    super(CancelRequestCDIBean::checkActionAllowed);
	}
    }

    static boolean checkActionAllowed(RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS)
		&& rrs != null
		&& rrs.isAnySelected() //
		&& rrs.getValueAsStream() //
			.allMatch(RequestRow::isCanCancelRequest) //
	;
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
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	try {
	    final List<RequestRow<?>> res = rrs.getValueAsStream() //
		    .map(r -> {
			try {
			    return insuranceRequests.requestCanceled(r.getEntity(), currentUser.getValue(), reason);
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
