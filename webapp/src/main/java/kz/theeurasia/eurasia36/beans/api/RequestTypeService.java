package kz.theeurasia.eurasia36.beans.api;

import java.util.List;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoVehicle;

import kz.theeurasia.eurasia36.beans.model.RequestRow;

public interface RequestTypeService {
    RequestType type(Request request);

    String displayName(Request request);

    boolean isA(Request request, RequestType type);

    List<CascoVehicle> cascoVehiclesAsList(Casco casco);

    String iconClass(RequestRow<?> row);

    String iconColor(RequestRow<?> row);

    boolean typeIs(RequestType value, RequestType expecting);

}
