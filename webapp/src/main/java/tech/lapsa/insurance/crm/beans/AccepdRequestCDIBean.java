package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.ProgressStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Named("acceptRequest")
@RequestScoped
public class AccepdRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
    }

    // CDIs

    // local

    @Inject
    private RequestHolder requestHolder;

    @Inject
    private CurrentUserHolder currentUser;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doAccept() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	Request request = requestHolder.getValue().getEntity();
	if (request != null && request.getProgressStatus() == ProgressStatus.NEW) {
	    final Instant now = Instant.now();
	    request.setProgressStatus(ProgressStatus.ON_PROCESS);
	    request.setAccepted(now);
	    request.setAcceptedBy(currentUser.getValue());
	    request.setUpdated(now);
	    try {
		requestDAO.save(request);
	    } catch (IllegalArgument e) {
		throw new FacesException(e);
	    }
	}
	return null;
    }
}
