package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.InsuranceRequest;

import kz.theeurasia.eurasia36.beans.api.WritableValueHolder;

@Named("insuranceRequest")
@ViewScoped
public class InsuranceRequestHolder extends DefaultWritableValueHolder<InsuranceRequest>
	implements Serializable, WritableValueHolder<InsuranceRequest> {
    private static final long serialVersionUID = -2574434730269891652L;

    public String getProductTypeVerb() {
	if (value == null || value.getProductType() == null)
	    return "none";
	return value.getProductType().getVerb();
    }

    public String getObtainingMethodVerb() {
	if (value == null || value.getObtaining() == null || value.getObtaining().getMethod() == null)
	    return "none";
	switch (value.getObtaining().getMethod()) {
	case DELIVERY:
	    return "delivery";
	case PICKUP:
	    return "pickup";
	default:
	    return "none";
	}
    }
}
