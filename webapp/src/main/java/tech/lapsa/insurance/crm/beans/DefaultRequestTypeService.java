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
import tech.lapsa.insurance.crm.rows.RequestRow;

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
    public boolean isTypeA(final RequestType sourceType, final RequestType targetType) {
	if (sourceType == null)
	    return false;
	if (targetType == null)
	    return false;
	switch (targetType) {
	case CALLBACK_REQUEST:
	    switch (sourceType) {
	    case CALLBACK_REQUEST:
		return true;
	    default:
		return false;
	    }
	case CASCO_REQUEST:
	    switch (sourceType) {
	    case CASCO_REQUEST:
		return true;
	    default:
		return false;
	    }
	case INSURANCE_REQUEST:
	    switch (sourceType) {
	    case POLICY_REQUEST:
	    case CASCO_REQUEST:
	    case CALLBACK_REQUEST:
	    case INSURANCE_REQUEST:
		return true;
	    default:
		return false;
	    }
	case POLICY_REQUEST:
	    switch (sourceType) {
	    case POLICY_REQUEST:
		return true;
	    default:
		return false;
	    }
	case REQUEST:
	    switch (sourceType) {
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
    public boolean isRequestA(final Request sourceRequest, final RequestType targetType) {
	if (sourceRequest == null)
	    return false;
	switch (targetType) {
	case CALLBACK_REQUEST:
	    return sourceRequest instanceof CallbackRequest;
	case CASCO_REQUEST:
	    return sourceRequest instanceof CascoRequest;
	case POLICY_REQUEST:
	    return sourceRequest instanceof PolicyRequest;
	case INSURANCE_REQUEST:
	    return sourceRequest instanceof InsuranceRequest;
	case REQUEST:
	    return sourceRequest instanceof Request;
	default:
	    return false;
	}
    }

    @Override
    public boolean isRowA(final RequestRow<?> sourceRow, final RequestType targetType) {
	if (sourceRow == null)
	    return false;
	final RequestType source = sourceRow.getType();
	return isTypeA(source, targetType);
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
