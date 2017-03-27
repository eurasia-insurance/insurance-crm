package kz.theeurasia.eurasia36.beans.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.casco.CascoVehicle;

public class CascoRequestRowDataModel extends InsuranceRequestRowDataModel<CascoRequest>
	implements RequestRow<CascoRequest> {

    public CascoRequestRowDataModel(CascoRequest entity) {
	super(entity);
    }

    @Override
    public List<CascoVehicle> getCascoVehiclesAsList() {
	try {
	    Casco casco = entity.getCasco();
	    CascoVehicle vehicle = casco.getInsuredVehicle();
	    return Collections.unmodifiableList(Arrays.asList(vehicle));
	} catch (NullPointerException e) {
	    return null;
	}
    }

}
