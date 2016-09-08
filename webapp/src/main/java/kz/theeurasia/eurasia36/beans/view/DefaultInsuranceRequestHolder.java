package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoVehicle;

import kz.theeurasia.eurasia36.beans.api.InsuranceRequestHolder;

@Named("insuranceRequest")
@ViewScoped
public class DefaultInsuranceRequestHolder extends DefaultWritableValueHolder<InsuranceRequest>
	implements Serializable, InsuranceRequestHolder {
    private static final long serialVersionUID = -2574434730269891652L;

    private static final String VERB_NONE = "none";

    @Override
    public String getProductTypeVerb() {
	if (value == null || value.getProductType() == null)
	    return VERB_NONE;
	switch (value.getProductType()) {
	case CASCO:
	    return "casco";
	case POLICY:
	    return "policy";
	default:
	    return VERB_NONE;
	}
    }

    @Override
    public String getPaymentMethodVerb() {
	if (value == null || value.getPayment() == null || value.getPayment().getMethod() == null)
	    return VERB_NONE;
	switch (value.getPayment().getMethod()) {
	case PAYCARD_ONLINE:
	    return "paycard-online";
	case PAYCASH:
	    return "paycash";
	default:
	    return VERB_NONE;
	}
    }

    @Override
    public String getObtainingMethodVerb() {
	if (value == null || value.getObtaining() == null || value.getObtaining().getMethod() == null)
	    return VERB_NONE;
	switch (value.getObtaining().getMethod()) {
	case DELIVERY:
	    return "delivery";
	case PICKUP:
	    return "pickup";
	default:
	    return VERB_NONE;
	}
    }

    @Override
    public List<CascoVehicle> getCascoVehiclesAsList() {
	if (value != null && value.getProduct() != null && value.getProduct() instanceof Casco) {
	    Casco casco = (Casco) value.getProduct();
	    CascoVehicle vehicle = casco.getInsuredVehicle();
	    return Arrays.asList(vehicle);
	}
	return null;
    }

    @Override
    public void reset() {
	this.value = null;
    }
}
