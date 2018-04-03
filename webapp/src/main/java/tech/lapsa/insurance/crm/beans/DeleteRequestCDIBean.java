package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
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
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.patterns.dao.NotFound;

@Named("deleteRequest")
@RequestScoped
public class DeleteRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("deleteRequestCheck")
    @Dependent
    public static class DeleteRequestCheckCDIBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// list

	private List<RequestRow<?>> list;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// CDIs

	@Inject
	private RequestHolder requestHolder;

	@PostConstruct
	public void init() {
	    list = MyCollections.orEmptyList(requestHolder.getValue());
	    allowed = isInRole(InsuranceRoleGroup.DELETERS) //
		    && !list.isEmpty() //
		    && list.stream() //
			    .map(RequestRow::getEntity) //
			    .allMatch(r -> {
				if (!(r instanceof InsuranceRequest))
				    return true;
				final TransactionStatus s = ((InsuranceRequest) r).getTransactionStatus();
				return s != null && s.equals(TransactionStatus.NOT_COMPLETED);
			    }) //
	    ;
	}
    }

    // CDIs

    // local

    @Inject
    private DeleteRequestCheckCDIBean check;

    @EJB
    private RequestDAORemote requestDAO;

    public String doDelete() throws FacesException, IllegalStateException, IllegalArgumentException {
	SecurityUtils.checkRoleGranted(InsuranceRoleGroup.DELETERS);

	if (!check.isAllowed())
	    throw MyExceptions.format(FacesException::new,
		    "Transaction status is invalid for deletion. Deletion is possible on '%1$s' only.",
		    TransactionStatus.NOT_COMPLETED);

	check.list.stream().map(RequestRow::getEntity).map(Request::getId).forEach(id -> {
	    try {
		requestDAO.deleteById(id);
	    } catch (IllegalArgument e1) {
		throw new FacesException(e1.getRuntime());
	    } catch (NotFound e) {
		throw new FacesException(e);
	    }
	});
	return null;
    }
}
