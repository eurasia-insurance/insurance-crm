package kz.theeurasia.eurasia36.beans.application;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.InsuredDriverData;
import com.lapsa.insurance.domain.InsuredVehicleData;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyRequestDAO;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.view.PolicyOrders;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    @EJB
    private PolicyRequestDAO policyExpressOrderDAO;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Inject
    private PolicyOrders policyOrders;

    @Override
    public String doCloseRequest(PolicyRequest order) {
	order.setRequestStatus(RequestStatus.CLOSED);
	try {
	    policyExpressOrderDAO.save(order);
	    policyOrders.forceRefresh();
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
	return null;
    }

    public boolean vehicleHasImages(InsuredVehicleData vehicle) {
	if (vehicle != null && vehicle.getVehicleData() != null && vehicle.getVehicleData().getCertificateData() != null
		&& vehicle.getVehicleData().getCertificateData().getScan() != null
		&& (vehicle.getVehicleData().getCertificateData().getScan().getFrontside() != null
			|| vehicle.getVehicleData().getCertificateData().getScan().getBackside() != null))
	    return true;
	return false;
    }

    public boolean driverHasImages(InsuredDriverData driver) {
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
