package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.PolicyExpressOrder;
import com.lapsa.insurance.persistence.dao.PolicyExpressOrderDAO;

@Named("policyOrders")
@ViewScoped
public class PolicyOrders implements Serializable {

    private static final long serialVersionUID = 7249376610273191727L;

    @EJB
    private PolicyExpressOrderDAO policyExpressOrderDAO;

    // GENERATED

    public List<PolicyExpressOrder> getExpressOrders() {
	return policyExpressOrderDAO.findAll();
    }
}
