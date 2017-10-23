package tech.lapsa.insurance.crm.beans;

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

import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.insurance.crm.beans.i.RequestTypeService;

@Named("rts")
@ApplicationScoped
public class DefaultRequestTypeService implements RequestTypeService {

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
    public boolean typeIs(RequestType value, RequestType expecting) {
	if (value == null)
	    return false;
	if (expecting == null)
	    return false;

	switch (expecting) {
	case CALLBACK_REQUEST:
	    switch (value) {
	    case CALLBACK_REQUEST:
		return true;
	    default:
		return false;
	    }
	case CASCO_REQUEST:
	    switch (value) {
	    case CASCO_REQUEST:
		return true;
	    default:
		return false;
	    }
	case INSURANCE_REQUEST:
	    switch (value) {
	    case POLICY_REQUEST:
	    case CASCO_REQUEST:
	    case INSURANCE_REQUEST:
		return true;
	    default:
		return false;
	    }
	case POLICY_REQUEST:
	    switch (value) {
	    case POLICY_REQUEST:
		return true;
	    default:
		return false;
	    }
	case REQUEST:
	    switch (value) {
	    case POLICY_REQUEST:
	    case CASCO_REQUEST:
	    case INSURANCE_REQUEST:
	    case CALLBACK_REQUEST:
	    case REQUEST:
		return true;
	    default:
		return false;
	    }
	default:
	    return false;
	}
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
    public List<CascoVehicle> cascoVehiclesAsList(Casco casco) {
	try {
	    CascoVehicle vehicle = casco.getInsuredVehicle();
	    return Collections.unmodifiableList(Arrays.asList(vehicle));
	} catch (NullPointerException e) {
	    return null;
	}
    }
}
