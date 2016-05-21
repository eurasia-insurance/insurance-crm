package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.PolicyExpressOrder;
import com.lapsa.insurance.persistence.dao.PolicyExpressOrderDAO;

@Named("policyOrders")
@RequestScoped
public class PolicyOrders implements Serializable {

    private static final long serialVersionUID = 7249376610273191727L;

    @EJB
    private PolicyExpressOrderDAO policyExpressOrderDAO;

    private List<PolicyExpressOrder> expressOrders;

    @PostConstruct
    public void init() {
	expressOrders = policyExpressOrderDAO.findAll();
    }

    // GENERATED

    public List<PolicyExpressOrder> getExpressOrders() {
	return expressOrders;
    }
}
