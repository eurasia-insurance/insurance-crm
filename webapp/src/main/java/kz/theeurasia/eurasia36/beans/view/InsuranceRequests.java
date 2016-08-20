package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyRequestDAO;

import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@Named("policyOrders")
@ViewScoped
public class PolicyOrders implements Serializable {

    private static final long serialVersionUID = 7249376610273191727L;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @EJB
    private PolicyRequestDAO policyRequestDAO;

    private RequestStatus statusFilter = RequestStatus.OPEN;

    private List<PolicyRequest> expressOrdersByStatus;

    public List<PolicyRequest> getExpressOrdersByStatus() {
	try {
	    if (expressOrdersByStatus == null)
		expressOrdersByStatus = policyRequestDAO.findByStatus(statusFilter);
	    return expressOrdersByStatus;
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(e);
	}
	return null;
    }

    public void forceRefresh() {
	expressOrdersByStatus = null;
    }

    // GENERATED

    public RequestStatus getStatusFilter() {
	return statusFilter;
    }

    public void setStatusFilter(RequestStatus statusFilter) {
	this.statusFilter = statusFilter;
	forceRefresh();
    }
}
