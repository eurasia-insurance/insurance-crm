package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.domain.policy.PolicyRequest;

import kz.theeurasia.eurasia36.beans.api.RequestType;

public class PolicyRequestRowDataModel extends InsuranceRequestRowDataModel<PolicyRequest>
	implements RequestRow<PolicyRequest> {

    public PolicyRequestRowDataModel(PolicyRequest entity) {
	super(entity);
    }

    @Override
    public RequestType getType() {
	return RequestType.POLICY_REQUEST;
    }
}
