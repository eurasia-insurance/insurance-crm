package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.domain.policy.PolicyRequest;

public class PolicyRequestRowDataModel extends InsuranceRequestRowDataModel<PolicyRequest>
	implements RequestRow<PolicyRequest> {

    public PolicyRequestRowDataModel(PolicyRequest entity) {
	super(entity);
    }
}
