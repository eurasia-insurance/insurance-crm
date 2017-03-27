package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.domain.casco.CascoRequest;

public class CascoRequestRowDataModel extends InsuranceRequestRowDataModel<CascoRequest>
	implements RequestRow<CascoRequest> {

    public CascoRequestRowDataModel(CascoRequest entity) {
	super(entity);
    }
}
