package tech.lapsa.insurance.crm.beans.i;

import java.util.List;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoVehicle;

public interface RequestTypeService {
    RequestType type(Request request);

    boolean isA(Request request, RequestType type);

    List<CascoVehicle> cascoVehiclesAsList(Casco casco);

    boolean typeIs(RequestType value, RequestType expecting);

}
