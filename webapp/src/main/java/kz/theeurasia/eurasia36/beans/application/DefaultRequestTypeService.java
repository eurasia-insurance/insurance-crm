package kz.theeurasia.eurasia36.beans.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.casco.CascoVehicle;
import com.lapsa.insurance.domain.policy.PolicyRequest;

import kz.theeurasia.eurasia36.beans.api.RequestType;
import kz.theeurasia.eurasia36.beans.api.RequestTypeService;
import kz.theeurasia.eurasia36.beans.model.RequestRow;

@Named("rts")
@ApplicationScoped
public class DefaultRequestTypeService implements RequestTypeService {

    private static final String ICON_STATUS_NEW = "icon-new";
    private static final String ICON_STATUS_ON_PROCESS = "icon-on-process";
    private static final String ICON_STATUS_ON_HOLD = "";
    private static final String ICON_STATUS_SUCCESS = "icon-success";
    private static final String ICON_STATUS_FAIL = "icon-fail";

    private static final String ICON_INSURANCE_REQUEST = "fa fa-calculator";
    private static final String ICON_CALLBACK_REQUEST = "fa fa-volume-control-phone";

    @Override
    public RequestType type(Request request) {
	if (request == null)
	    return null;
	if (request instanceof PolicyRequest)
	    return RequestType.POLICY_REQUEST;
	if (request instanceof CascoRequest)
	    return RequestType.CASCO_REQUEST;
	if (request instanceof CallbackRequest)
	    return RequestType.CALLBACK_REQUEST;
	return RequestType.REQUEST;
    }

    @Override
    public boolean isA(Request request, RequestType type) {
	if (request == null)
	    return false;
	switch (type) {
	case CALLBACK_REQUEST:
	    return request instanceof CallbackRequest;
	case CASCO_REQUEST:
	    return request instanceof CascoRequest;
	case POLICY_REQUEST:
	    return request instanceof PolicyRequest;
	case INSURANCE_REQUEST:
	    return request instanceof InsuranceRequest;
	case REQUEST:
	    return request instanceof Request;
	default:
	    return false;
	}
    }

    @Override
    public String displayName(Request request) {
	RequestType type = type(request);
	if (type == null)
	    return null;
	return type.name();
    }

    @Override
    public List<CascoVehicle> cascoVehiclesAsList(Casco casco) {
	try {
	    CascoVehicle vehicle = casco.getInsuredVehicle();
	    return Collections.unmodifiableList(Arrays.asList(vehicle));
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String iconClass(RequestRow<?> row) {
	if (isA(row.getEntity(), RequestType.CALLBACK_REQUEST))
	    return ICON_CALLBACK_REQUEST;
	if (isA(row.getEntity(), RequestType.INSURANCE_REQUEST))
	    return ICON_INSURANCE_REQUEST;
	return null;
    }

    @Override
    public String iconColor(RequestRow<?> row) {
	switch (row.getProgressStatus()) {
	case NEW:
	    return ICON_STATUS_NEW;
	case ON_PROCESS:
	    return ICON_STATUS_ON_PROCESS;
	case ON_HOLD:
	    return ICON_STATUS_ON_HOLD;
	case FINISHED:
	    if (row.getTransactionStatus() == null)
		return ICON_STATUS_SUCCESS;
	    switch (row.getTransactionStatus()) {
	    case NOT_COMPLETED:
		return ICON_STATUS_FAIL;
	    case COMPLETED:
	    default:
		return ICON_STATUS_SUCCESS;
	    }
	}
	return null;
    }

}
