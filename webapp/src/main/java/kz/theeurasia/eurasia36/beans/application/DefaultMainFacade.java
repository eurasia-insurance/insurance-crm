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
import com.lapsa.insurance.persistence.dao.NotPersistedException;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyRequestDAO;
import com.lapsa.insurance.persistence.dao.filter.InsuranceRequestFitler;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.api.InsuranceRequestHolder;
import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsFilterHolder;
import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsHolder;
import kz.theeurasia.eurasia36.beans.view.pojo.DefaultInsuranceRequestFitler;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    @Override
    public void onFilterChanged(AjaxBehaviorEvent event) {
	refreshRequests();
    }

    @Override
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
    public String doSaveRequest() {
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doResetRequest() {
	reloadRequest();
	refreshRequests();
	return null;
    }

    // PRIVATE

    @Inject
    private InsuranceRequestsHolder insuranceRequestsHolder;

    @Inject
    private InsuranceRequestsFilterHolder insuranceRequestsFilterHolder;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Inject
    private InsuranceRequestDAO insuranceRequestDAO;

    @Inject
    private PolicyRequestDAO policyRequestDAO;

    @Inject
    private CascoRequestDAO cascoRequestDAO;

    @Inject
    private InsuranceRequestHolder insuranceRequestHolder;

    private void saveRequest() {
	try {
	    insuranceRequestHolder.setValue(insuranceRequestDAO.save(insuranceRequestHolder.getValue()));
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void reloadRequest() {
	try {
	    insuranceRequestHolder.setValue(insuranceRequestDAO.restore(insuranceRequestHolder.getValue()));
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	} catch (NotPersistedException e) {
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
