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

@Named("requestTypeService")
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
	if (request instanceof InsuranceRequest)
	    return RequestType.INSURANCE_REQUEST;
	return RequestType.REQUEST;
    }

    @Override
    public String displayName(Request request) {
	RequestType type = type(request);
	if (type == null)
	    return null;
	return type.name();
    }

}
