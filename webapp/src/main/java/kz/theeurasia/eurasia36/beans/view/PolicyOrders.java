package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.PolicyExpressOrder;
import com.lapsa.insurance.persistence.dao.PolicyExpressOrderDAO;

@Named("policyOrders")
@ViewScoped
public class PolicyOrders implements Serializable {

    private static final long serialVersionUID = 7249376610273191727L;

    @EJB
    private PolicyExpressOrderDAO policyExpressOrderDAO;

    private RequestStatus statusFilter = RequestStatus.OPEN;

    private List<PolicyExpressOrder> expressOrdersByStatus;

    public List<PolicyExpressOrder> getExpressOrdersByStatus() {
	if (expressOrdersByStatus == null)
	    expressOrdersByStatus = policyExpressOrderDAO.findByStatus(statusFilter);
	return expressOrdersByStatus;
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
