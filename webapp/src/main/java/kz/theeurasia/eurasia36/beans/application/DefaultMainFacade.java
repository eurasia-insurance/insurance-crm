package kz.theeurasia.eurasia36.beans.application;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.PolicyExpressOrder;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyExpressOrderDAO;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    @EJB
    private PolicyExpressOrderDAO policyExpressOrderDAO;
    
    @Inject
    private FacesMessagesFacade facesMessagesFacade;
    
    @Override
    public void onRequestStatusChanged(PolicyExpressOrder order) {
	try {
	    policyExpressOrderDAO.save(order);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }
}
