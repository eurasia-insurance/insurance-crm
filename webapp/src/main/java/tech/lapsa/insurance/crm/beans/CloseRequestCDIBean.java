package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.annotation.PostConstruct;
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
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;

@Named("closeRequest")
@RequestScoped
public class CloseRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("closeRequestCheck")
    @Dependent
    public static class CloseRequestCheckCDIBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// list

	private List<RequestRow<?>> list;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// CDIs

	// local

	@Inject
	private RequestHolder requestHolder;

	@PostConstruct
	public void init() {
	    list = MyCollections.orEmptyList(requestHolder.getValue());
	    allowed = isInRole(InsuranceRoleGroup.CLOSERS)
		    && !list.isEmpty() //
		    && list.stream() //
			    .allMatch(RequestRow::isCanClose) //
	    ;
	}

    }

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private CloseRequestCheckCDIBean check;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doClose() {
	checkRoleGranted(InsuranceRoleGroup.CLOSERS);

	if (!check.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Status is invalid for archiving. Archiving is posible at '%1$s' and '%2$s' only.",
		    RequestStatus.OPEN, ProgressStatus.FINISHED);

	final Instant now = Instant.now();
	try {
	    requestDAO.saveAll(
		    check.list.stream() //
			    .map(RequestRow::getEntity) //
			    .peek(r -> r.setStatus(RequestStatus.CLOSED))
			    .peek(r -> r.setClosed(now))
			    .peek(r -> r.setClosedBy(currentUser.getValue()))
			    .collect(MyCollectors.unmodifiableList()));
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}
	return null;
    }
}
