package tech.lapsa.insurance.crm.beans.i;

import java.util.List;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoVehicle;

import tech.lapsa.insurance.crm.rows.RequestRow;

public interface RequestTypeService {
    boolean isTypeA(RequestType sourceType, RequestType targetType);

    boolean isRequestA(Request sourceRequest, RequestType targetType);

    boolean isRowA(RequestRow<?> sourceRow, RequestType targetType);

    RequestType type(Request request);

    List<CascoVehicle> cascoVehiclesAsList(Casco casco);
}
