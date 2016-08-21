package kz.theeurasia.eurasia36.beans.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;
import com.lapsa.insurance.persistence.dao.InsuranceRequestDAO;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.view.InsuranceRequests;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    @Inject
    private InsuranceRequestDAO insuranceRequestDAO;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Inject
    private InsuranceRequests insuranceRequests;

    @Override
    public String doCloseRequest(InsuranceRequest request) {
	request.setRequestStatus(RequestStatus.CLOSED);
	try {
	    insuranceRequestDAO.save(request);
	    insuranceRequests.forceRefresh();
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
	return null;
    }

    public boolean vehicleHasImages(PolicyVehicle vehicle) {
	if (vehicle != null && vehicle.getCertificateData() != null
		&& vehicle.getCertificateData().getScan() != null
		&& (vehicle.getCertificateData().getScan().getFrontside() != null
			|| vehicle.getCertificateData().getScan().getBackside() != null))
	    return true;
	return false;
    }

    public boolean driverHasImages(PolicyDriver driver) {
	if (driver != null && driver.getDriverLicenseData() != null && driver.getDriverLicenseData().getScan() != null
		&& (driver.getDriverLicenseData().getScan().getFrontside() != null
			|| driver.getDriverLicenseData().getScan().getBackside() != null))
	    return true;
	if (driver != null && driver.getIdentityCardData() != null && driver.getIdentityCardData().getScan() != null
		&& (driver.getIdentityCardData().getScan().getFrontside() != null
			|| driver.getIdentityCardData().getScan().getBackside() != null))
	    return true;
	return false;
    }

}
