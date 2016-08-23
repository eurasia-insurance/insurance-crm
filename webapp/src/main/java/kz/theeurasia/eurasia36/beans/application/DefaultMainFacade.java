package kz.theeurasia.eurasia36.beans.application;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.persistence.dao.CascoRequestDAO;
import com.lapsa.insurance.persistence.dao.InsuranceRequestDAO;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyRequestDAO;
import com.lapsa.insurance.persistence.dao.filter.InsuranceRequestFitler;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.view.DefaultInsuranceRequestFitler;
import kz.theeurasia.eurasia36.beans.view.InsuranceRequestsFilterHolder;
import kz.theeurasia.eurasia36.beans.view.InsuranceRequestsHolder;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    @Inject
    private InsuranceRequestsHolder insuranceRequestsHolder;

    @Inject
    private InsuranceRequestsFilterHolder insuranceRequestsFilterHolder;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Override
    public void onFilterChanged(AjaxBehaviorEvent event) {
	refreshRequests();
    }

    public void onRequestStatusFilterChanged(AjaxBehaviorEvent event) {
	fireRequestStatusFilterChanged();
	refreshRequests();
    }

    @Override
    public String doInitialize() {
	resetFilter();
	refreshRequests();
	return null;
    }

    @Override
    public String doResetFilter() {
	resetFilter();
	refreshRequests();
	return null;
    }

    @Override
    public String doCloseRequest(InsuranceRequest request) {
	closeRequest(request);
	refreshRequests();
	return null;
    }

    // PRIVATE

    @Inject
    private InsuranceRequestDAO insuranceRequestDAO;

    @Inject
    private PolicyRequestDAO policyRequestDAO;

    @Inject
    private CascoRequestDAO cascoRequestDAO;

    private void closeRequest(InsuranceRequest request) {
	request.setRequestStatus(RequestStatus.CLOSED);
	try {
	    insuranceRequestDAO.save(request);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void fireRequestStatusFilterChanged() {
	if (RequestStatus.OPEN.equals(insuranceRequestsFilterHolder.getRequestStatus()))
	    insuranceRequestsFilterHolder.setClosingResult(null);
    }

    private void resetFilter() {
	insuranceRequestsFilterHolder.setValue(new DefaultInsuranceRequestFitler());
	insuranceRequestsFilterHolder.setRequestStatus(RequestStatus.OPEN);
    }

    private void refreshRequests() {
	InsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	InsuranceProductType productType = insuranceRequestsFilterHolder.getValue().getInsuranceProductType();
	List<InsuranceRequest> requests = null;
	if (productType == null)
	    requests = insuranceRequestDAO.findByFilter(filter);
	else
	    switch (productType) {
	    case CASCO:
		requests = new ArrayList<>();
		List<CascoRequest> cascos = cascoRequestDAO.findByFilter(filter);
		requests.addAll(cascos);
		break;
	    case POLICY:
		requests = new ArrayList<>();
		List<PolicyRequest> policies = policyRequestDAO.findByFilter(filter);
		requests.addAll(policies);
		break;
	    }
	insuranceRequestsHolder.setRequests(requests);
    }
}
