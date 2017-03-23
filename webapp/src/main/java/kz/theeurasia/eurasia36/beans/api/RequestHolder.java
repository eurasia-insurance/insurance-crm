package kz.theeurasia.eurasia36.beans.api;

import java.util.List;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.CascoVehicle;

public interface RequestHolder extends WritableValueHolder<Request> {

    List<CascoVehicle> getCascoVehiclesAsList();

    RequestType getRequestType();

}
