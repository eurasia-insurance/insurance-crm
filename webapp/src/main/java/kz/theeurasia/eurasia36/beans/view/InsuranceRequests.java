package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.persistence.dao.InsuranceRequestDAO;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;

import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@Named("insuranceRequests")
@ViewScoped
public class InsuranceRequests implements Serializable {

    private static final long serialVersionUID = 7249376610273191727L;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @EJB
    private InsuranceRequestDAO insuranceRequestDAO;

    private RequestStatus statusFilter = RequestStatus.OPEN;

    private List<InsuranceRequest> requestsByStatus;

    public List<InsuranceRequest> getInsuranceRequestByStatus() {
	try {
	    if (requestsByStatus == null)
		requestsByStatus = insuranceRequestDAO.findByStatus(statusFilter);
	    return requestsByStatus;
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(e);
	}
	return null;
    }

    public void forceRefresh() {
	requestsByStatus = null;
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
