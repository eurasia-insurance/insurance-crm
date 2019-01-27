package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.InsuranceRequestStatus.*;
import static com.lapsa.insurance.elements.ProgressStatus.*;
import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
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

@Named("archiveRequest")
@RequestScoped
public class ArchiveRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("archiveRequestCheck")
    @Dependent
    public static class ArchiveRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public ArchiveRequestCheckCDIBean() {
	    super(ArchiveRequestCDIBean::checkActionAllowed);
	}

    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.progressIn(FINISHED)
	    && rr.insuranceRequestIn(PREMIUM_PAID, REQUEST_CANCELED);

    private static boolean checkActionAllowed(RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CLOSERS)
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
    private ArchiveRequestCheckCDIBean checker;

    @Inject
    private RequestsSelectionCDIBean rrs;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doAction() {
	rrs.refresh();

	if (!checker.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Status is invalid for archiving. Archiving is posible at '%1$s' only.", ProgressStatus.FINISHED);

	try {
	    // final List<RequestRow<?>> res =
	    rrs.getValueAsStream() //
		    .map(RequestRow::getEntity) //
		    .peek(r -> r.setArchived(true))
		    .map(r -> {
			try {
			    return requestDAO.save(r);
			} catch (IllegalArgument e1) {
			    throw new FacesException(e1.getRuntime());
			}
		    })
		    .map(RequestRow::from)
		    .collect(MyCollectors.unmodifiableList());
	    // rrs.setValue(res);
	    rrs.reset();
	} finally {
	    // rrs.reset();
	}

	return null;
    }
}
