package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.PolicyExpressOrder;
import com.lapsa.insurance.persistence.dao.PolicyExpressOrderDAO;

@Named("policyRequests")
@ViewScoped
public class PolicyRequests implements Serializable {

    private static final long serialVersionUID = 7249376610273191727L;

    @EJB
    private PolicyExpressOrderDAO policyExpressOrcerDAO;

    private List<PolicyExpressOrder> allOpen;

    @PostConstruct
    public void init() {
	allOpen = policyExpressOrcerDAO.findAllOpen();
    }

    // GENERATED

    public List<PolicyExpressOrder> getAllOpen() {
	return allOpen;
    }
}
