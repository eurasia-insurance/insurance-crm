package kz.theeurasia.eurasia36.beans.model;

import java.util.Collections;
import java.util.List;

import com.lapsa.insurance.domain.casco.CascoVehicle;
import com.lapsa.insurance.domain.policy.PolicyRequest;

public class PolicyRequestRowDataModel extends InsuranceRequestRowDataModel<PolicyRequest>
	implements RequestRow<PolicyRequest> {

    public PolicyRequestRowDataModel(PolicyRequest entity) {
	super(entity);
    }

    @Override
    public List<CascoVehicle> getCascoVehiclesAsList() {
	return Collections.emptyList();
    }
}
