package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.TransactionStatus;
import com.lapsa.utils.security.SecurityUtils;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.patterns.dao.NotFound;

@Named("requestDelete")
@RequestScoped
public class RequestDeleteCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // controls

    @PostConstruct
    public void init() {
	SecurityUtils.checkRoleGranted(InsuranceRoleGroup.DELETERS);
    }

    // CDIs

    // local

    @Inject
    private RequestHolder requestHolder;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doDelete() throws FacesException, IllegalStateException, IllegalArgumentException {
	final Request r = requestHolder.getValue().getEntity();
	if (r instanceof InsuranceRequest) {
	    final TransactionStatus s = ((InsuranceRequest) r).getTransactionStatus();
	    if (s == null || !s.equals(TransactionStatus.NOT_COMPLETED))
		throw new FacesException(MyExceptions.illegalStateFormat(
			"Transaction status is invalid for deletion '%1$s'. Deletion requests allowed with '%2$s' only.",
			s, TransactionStatus.NOT_COMPLETED));
	}

	try {
	    requestDAO.deleteById(r.getId());
	} catch (IllegalArgument e1) {
	    throw new FacesException(e1.getRuntime());
	} catch (NotFound e) {
	    throw new FacesException(e);
	}
	requestHolder.setValue(null);
	return null;
    }
}
