package kz.theeurasia.eurasia36.beans.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.crm.ProgressStatus;
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
    public String doRefresh() {
	refreshRequests();
	return null;
    }

    @Override
    public String doInitialize() {
	initFilter();
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
    public String doAcceptRequest() {
	acceptRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doPauseRequest() {
	pauseRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doResumeRequest() {
	resumeRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doCloseRequest() {
	closeRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doCancelEditRequest() {
	resetRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doCompleteRequest() {
	completeRequest();
	saveRequest();
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

    private void initFilter() {
	resetFilter();
	insuranceRequestsFilterHolder.setRequestStatus(RequestStatus.OPEN);
    }

    private void resetFilter() {
	RequestStatus last = insuranceRequestsFilterHolder.getRequestStatus();
	insuranceRequestsFilterHolder.setValue(new DefaultInsuranceRequestFitler());
	insuranceRequestsFilterHolder.setRequestStatus(last);
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

    private void saveRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	try {
	    insuranceRequest.setUpdated(new Date());
	    InsuranceRequest insuranceRequestSaved = insuranceRequestDAO.save(insuranceRequest);
	    insuranceRequestHolder.setValue(insuranceRequestSaved);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void resetRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	try {
	    InsuranceRequest insuranceRequestSaved = insuranceRequestDAO.restore(insuranceRequest);
	    insuranceRequestHolder.setValue(insuranceRequestSaved);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	} catch (NotPersistedException e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void closeRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setClosed(new Date());
	insuranceRequest.setStatus(RequestStatus.CLOSED);
    }

    private void acceptRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_PROCESS);
	insuranceRequest.setAccepted(new Date());
    }

    private void resumeRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_PROCESS);
    }

    private void pauseRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_HOLD);
    }

    private void completeRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.FINISHED);
	insuranceRequest.setCompleted(new Date());
    }
}
