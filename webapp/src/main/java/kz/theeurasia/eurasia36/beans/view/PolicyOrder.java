package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.PolicyExpressOrder;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyExpressOrderDAO;

import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@Named("policyOrder")
@ViewScoped
public class PolicyOrder implements Serializable {
    private static final long serialVersionUID = -2574434730269891652L;

    private PolicyExpressOrder currentOrder;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @EJB
    private PolicyExpressOrderDAO policyExpressOrderDAO;

    public PolicyExpressOrder getCurrentOrder() {
	return currentOrder;
    }

    public void setCurrentOrder(PolicyExpressOrder currentOrder) {
	this.currentOrder = currentOrder;
    }

    public String doSave() {
	try {
	    currentOrder = policyExpressOrderDAO.save(currentOrder);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(e);
	}
	return null;
    }

    public static void main(String[] args) {
	PolicyExpressOrder z = new PolicyExpressOrder();

	z.getRequester().getEmail();
	z.getRequester().getName();
	z.getRequester().getPhone();
	z.getRequester().getPreferLanguage();

	z.getId();
	
	z.getClosed();
	z.getCreated();
	z.getUpdated();
	
	z.getRequestStatus();
	z.getClosingResult();
	
	z.getObtaining();
	
	z.getPolicy().getInsuredDrivers().get(0).getAgeClass();
	z.getPolicy().getInsuredDrivers().get(0).getDriverLicenseData();
	z.getPolicy().getInsuredDrivers().get(0).getInsuranceClassType();
	z.getPolicy().getInsuredDrivers().get(0).getAgeClass();
	z.getPolicy().getInsuredDrivers().get(0).getExpirienceClass().canonicalName();
	z.getPolicy().getInsuredVehicles().get(0).getRegion();
	z.getPolicy().getInsuredVehicles().get(0).getCity();
	z.getPolicy().getInsuredVehicles().get(0).getVehicleAgeClass();
	z.getPolicy().getInsuredVehicles().get(0).getVehicleClass();
	
	
	z.getObtaining().getPickupPOS().getAddress().getCity();
	
	

    }
}
