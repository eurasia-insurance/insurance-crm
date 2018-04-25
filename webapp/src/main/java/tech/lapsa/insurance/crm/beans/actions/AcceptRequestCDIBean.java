package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.elements.ProgressStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;

@Named("acceptRequest")
@RequestScoped
public class AcceptRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("acceptRequestCheck")
    @Dependent
    public static class AccepdRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public AccepdRequestCheckCDIBean() {
	    super(AcceptRequestCDIBean::actionAllowed);
	}
    }

    static boolean actionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS)
		&& rrs != null
		&& rrs.notEmptyValue() //
		&& rrs.getValueAsStream() //
			.allMatch(RequestRow::isCanAccept);
    }

    // CDIs

    // local

    @Inject
    private AccepdRequestCheckCDIBean checker;

    @Inject
    private RequestsSelectionCDIBean rrs;

    @Inject
    private CurrentUserHolder currentUser;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doAccept() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checker.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Progress status is invalid for accepting. Accepting is posible at '%1$s' only.",
		    ProgressStatus.NEW);

	final Instant now = Instant.now();
	try {
	    final List<RequestRow<?>> res = rrs.getValueAsStream() //
		    .map(RequestRow::getEntity) //
		    .peek(r -> r.setProgressStatus(ProgressStatus.ON_PROCESS))
		    .peek(r -> r.setAccepted(now))
		    .peek(r -> r.setAcceptedBy(currentUser.getValue()))
		    .map(r -> {
			try {
			    return requestDAO.save(r);
			} catch (IllegalArgument e1) {
			    throw new FacesException(e1.getRuntime());
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
