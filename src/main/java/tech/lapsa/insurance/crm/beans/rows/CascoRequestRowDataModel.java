package tech.lapsa.insurance.crm.beans.rows;

import com.lapsa.insurance.domain.casco.CascoRequest;

import tech.lapsa.insurance.crm.beans.i.RequestType;

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
