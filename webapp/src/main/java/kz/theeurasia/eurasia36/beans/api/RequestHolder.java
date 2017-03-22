package kz.theeurasia.eurasia36.beans.api;

import java.util.List;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.casco.CascoVehicle;

public interface InsuranceRequestHolder extends WritableValueHolder<InsuranceRequest> {

    List<CascoVehicle> getCascoVehiclesAsList();

}
