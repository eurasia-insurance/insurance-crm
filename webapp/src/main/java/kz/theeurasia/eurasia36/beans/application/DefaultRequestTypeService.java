package kz.theeurasia.eurasia36.beans.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.policy.PolicyRequest;

import kz.theeurasia.eurasia36.beans.api.RequestType;
import kz.theeurasia.eurasia36.beans.api.RequestTypeService;

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

}
