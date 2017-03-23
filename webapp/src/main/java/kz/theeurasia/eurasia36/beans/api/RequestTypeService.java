package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.domain.Request;

public interface RequestTypeService {
    RequestType type(Request request);

    String displayName(Request request);
}
