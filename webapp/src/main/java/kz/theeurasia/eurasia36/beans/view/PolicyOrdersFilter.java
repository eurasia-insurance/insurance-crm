package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.crm.RequestStatus;

@Named("policyOrdersFilter")
@ViewScoped
public class PolicyOrdersFilter implements Serializable {

    private static final long serialVersionUID = -6980458753963030228L;

    private RequestStatus[] requestStatuses;

    public RequestStatus[] getRequestStatuses() {
	return requestStatuses;
    }

    public void setRequestStatuses(RequestStatus[] requestStatuses) {
	this.requestStatuses = requestStatuses;
    }

}
