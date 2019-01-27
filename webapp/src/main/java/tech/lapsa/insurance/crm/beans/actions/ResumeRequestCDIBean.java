package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.ProgressStatus.ON_HOLD;
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

import com.lapsa.insurance.elements.ProgressStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;

@Named("resumeRequest")
@RequestScoped
public class ResumeRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("resumeRequestCheck")
    @Dependent
    public static class ResumeRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResumeRequestCheckCDIBean() {
	    super(ResumeRequestCDIBean::checkActionAllowed);
	}
    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.progressIn(ON_HOLD);

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

    public String doResume() {
	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new,
		    "Progress status is invalid for resuming. Resuming is posible at '%1$s' only.",
		    ProgressStatus.ON_HOLD);

	try {
	    final List<RequestRow<?>> res = rrs.getValueAsStream() //
		    .map(RequestRow::getEntity) //
		    .peek(r -> r.setProgressStatus(ProgressStatus.ON_PROCESS))
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
