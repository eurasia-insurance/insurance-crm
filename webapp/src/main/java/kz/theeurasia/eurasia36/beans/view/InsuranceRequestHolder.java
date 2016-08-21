package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyRequestDAO;

import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@Named("policyOrder")
@ViewScoped
public class PolicyOrder implements Serializable {
    private static final long serialVersionUID = -2574434730269891652L;

    private PolicyRequest currentOrder;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @EJB
    private PolicyRequestDAO policyExpressOrderDAO;

    public PolicyRequest getCurrentOrder() {
	return currentOrder;
    }

    public void setCurrentOrder(PolicyRequest currentOrder) {
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
}
