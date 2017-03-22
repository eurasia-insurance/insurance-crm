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
