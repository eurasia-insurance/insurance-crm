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
import com.lapsa.insurance.elements.RequestStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;

@Named("closeRequest")
@RequestScoped
public class CloseRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("closeRequestCheck")
    @Dependent
    public static class CloseRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkActionAllowed() {
	    return isInRole(InsuranceRoleGroup.CLOSERS)
		    && !getList().isEmpty() //
		    && getListStream() //
			    .allMatch(RequestRow::isCanClose) //
	    ;
	}
    }

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private CloseRequestCheckCDIBean checker;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doClose() {
	checkRoleGranted(InsuranceRoleGroup.CLOSERS);

	checker.refreshList();

	if (!checker.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Status is invalid for archiving. Archiving is posible at '%1$s' and '%2$s' only.",
		    RequestStatus.OPEN, ProgressStatus.FINISHED);

	final Instant now = Instant.now();
	try {
	    final List<RequestRow<?>> res = checker.getListStream() //
		    .map(RequestRow::getEntity) //
		    .peek(r -> r.setStatus(RequestStatus.CLOSED))
		    .peek(r -> r.setClosed(now))
		    .peek(r -> r.setClosedBy(currentUser.getValue()))
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
