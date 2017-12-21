package tech.lapsa.insurance.crm.beans.rows;

import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyRequest;

import tech.lapsa.insurance.crm.beans.i.RequestType;

public class PolicyRequestRowDataModel extends InsuranceRequestRowDataModel<PolicyRequest>
	implements RequestRow<PolicyRequest> {

    public PolicyRequestRowDataModel(PolicyRequest entity) {
	super(entity);
    }

    @Override
    public RequestType getType() {
	return RequestType.POLICY_REQUEST;
    }

    @Override
    public Policy getPolicy() {
	try {
	    return entity.getPolicy();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Casco getCasco() {
	return null;
    }
}
