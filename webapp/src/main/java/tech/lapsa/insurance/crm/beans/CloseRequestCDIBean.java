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
import com.lapsa.insurance.elements.RequestStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Named("closeRequest")
@RequestScoped
public class CloseRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {
	checkRoleGranted(InsuranceRoleGroup.CLOSERS);
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

    public String doClose() {
	checkRoleGranted(InsuranceRoleGroup.CLOSERS);
	Request request = requestHolder.getValue().getEntity();
	if (request != null && request.getStatus() == RequestStatus.OPEN
		&& request.getProgressStatus() == ProgressStatus.FINISHED) {
	    final Instant now = Instant.now();
	    request.setStatus(RequestStatus.CLOSED);
	    request.setClosed(now);
	    request.setClosedBy(currentUser.getValue());
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
