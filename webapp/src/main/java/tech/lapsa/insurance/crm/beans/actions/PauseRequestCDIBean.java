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

import com.lapsa.insurance.elements.ProgressStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;

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

	@Override
	protected boolean checkActionAllowed() {
	    return isInRole(InsuranceRoleGroup.CHANGERS) //
		    && !getList().isEmpty()
		    && getListStream() //
			    .allMatch(RequestRow::isCanPause) //
	    ;
	}
    }

    // CDIs

    // local

    @Inject
    private PauseRequestCheckCDIBean checker;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doPause() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	checker.refreshList();

	if (!checker.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Progress status is invalid for pausing. Pausing is posible at '%1$s' only.",
		    ProgressStatus.ON_PROCESS);

	try {
	    final List<RequestRow<?>> res = checker.getListStream() //
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
	    checker.updateList(res);
	} finally {
	    // check.clearSelected();
	}
	return null;
    }
}
