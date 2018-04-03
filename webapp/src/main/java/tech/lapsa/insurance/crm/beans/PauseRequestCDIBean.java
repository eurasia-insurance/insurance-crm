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

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.ProgressStatus;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyExceptions;

@Named("pauseRequest")
@RequestScoped
public class PauseRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("pauseRequestCheck")
    @Dependent
    public static class PauseRequestCheckCDIBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// list

	private List<RequestRow<?>> list;

	// CDIs

	@Inject
	private RequestHolder requestHolder;

	@PostConstruct
	public void init() {
	    checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	    list = MyCollections.orEmptyList(requestHolder.getValue());
	    allowed = isInRole(InsuranceRoleGroup.CHANGERS) //
		    && !list.isEmpty()
		    && list.stream() //
			    .map(RequestRow::getEntity) //
			    .map(Request::getProgressStatus)
			    .allMatch(ProgressStatus.ON_PROCESS::equals) //
	    ;
	}
    }

    // CDIs

    // local

    @Inject
    private PauseRequestCheckCDIBean check;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    public String doPause() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	if (!check.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Progress status is invalid for pausing. Pausing is posible at '%1$s' only.",
		    ProgressStatus.ON_PROCESS);

	try {
	    requestDAO.saveAll(
		    check.list.stream() //
			    .map(RequestRow::getEntity) //
			    .peek(r -> r.setProgressStatus(ProgressStatus.ON_HOLD))
			    .peek(r -> r.setUpdated(Instant.now()))
			    .collect(MyCollectors.unmodifiableList()));
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}
	return null;
    }
}
