package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.TransactionStatus;
import com.lapsa.utils.security.SecurityUtils;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.patterns.dao.NotFound;

@Named("deleteRequest")
@RequestScoped
public class DeleteRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("deleteRequestCheck")
    @Dependent
    public static class DeleteRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	public DeleteRequestCheckCDIBean() {
	    super(DeleteRequestCDIBean::checkActionAllowed);
	}
    }

    static boolean checkActionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.DELETERS) //
		&& rrs != null
		&& rrs.isAnySelected() //
		&& rrs.getValueAsStream() //
			.allMatch(RequestRow::isCanDelete) //
	;
    }

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean rrs;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doDelete() throws FacesException, IllegalStateException, IllegalArgumentException {
	SecurityUtils.checkRoleGranted(InsuranceRoleGroup.DELETERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new,
		    "Transaction status is invalid for deletion. Deletion is possible on '%1$s' only.",
		    TransactionStatus.NOT_COMPLETED);

	try {
	    rrs.getValueAsStream() //
		    .map(RequestRow::getEntity) //
		    .map(Request::getId) //
		    .forEach(id -> {
			try {
			    requestDAO.deleteById(id);
			} catch (IllegalArgument e1) {
			    throw new FacesException(e1.getRuntime());
			} catch (NotFound e) {
			    throw new FacesException(e);
			}
		    });
	    rrs.reset();
	} finally {
	    // rrs.reset();
	}
	return null;
    }
}
