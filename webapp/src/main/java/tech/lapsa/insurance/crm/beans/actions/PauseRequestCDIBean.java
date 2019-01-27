package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.ProgressStatus.ON_PROCESS;
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

import com.lapsa.insurance.elements.InsuranceRequestStatus;
import com.lapsa.insurance.elements.ProgressStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;

@Named("pauseRequest")
@RequestScoped
public class PauseRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("pauseRequestCheck")
    @Dependent
    public static class PauseRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public PauseRequestCheckCDIBean() {
	    super(PauseRequestCDIBean::checkActionAllowed);
	}
    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.progressIn(ON_PROCESS)
	    && rr.insuranceRequestIn(InsuranceRequestStatus.PENDING, InsuranceRequestStatus.POLICY_ISSUED);

    private static boolean checkActionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS)
		&& MyOptionals.of(rrs)
			.filter(RequestsSelectionCDIBean::isAnySelected)
			.map(RequestsSelectionCDIBean::getValueAsStream)
			.map(s -> s.allMatch(ROW_ALLOWED))
			.orElse(Boolean.FALSE)
			.booleanValue();
    }

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean rrs;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doPause() {
	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new,
		    "Progress status is invalid for pausing. Pausing is posible at '%1$s' only.",
		    ProgressStatus.ON_PROCESS);

	try {
	    final List<RequestRow<?>> res = rrs.getValueAsStream() //
		    .map(RequestRow::getEntity) //
		    .peek(r -> r.setProgressStatus(ProgressStatus.ON_HOLD))
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
