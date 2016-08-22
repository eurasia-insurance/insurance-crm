package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.InsuranceRequest;

import kz.theeurasia.eurasia36.beans.api.WritableValueHolder;

@Named("insuranceRequests")
@ViewScoped
public class InsuranceRequestsHolder extends DefaultWritableValueHolder<List<InsuranceRequest>>
	implements Serializable, WritableValueHolder<List<InsuranceRequest>> {

    private static final long serialVersionUID = 7249376610273191727L;

    public List<InsuranceRequest> getRequests() {
	return super.getValue();
    }

    public void setRequests(List<InsuranceRequest> requests) {
	super.setValue(requests);
    }

}
