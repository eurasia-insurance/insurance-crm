package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.domain.casco.CascoRequest;

import kz.theeurasia.eurasia36.beans.api.RequestType;

public class CascoRequestRowDataModel extends InsuranceRequestRowDataModel<CascoRequest>
	implements RequestRow<CascoRequest> {

    public CascoRequestRowDataModel(CascoRequest entity) {
	super(entity);
    }

    @Override
    public RequestType getType() {
	return RequestType.CASCO_REQUEST;
    }
}
