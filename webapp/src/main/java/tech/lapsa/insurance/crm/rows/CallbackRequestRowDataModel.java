package tech.lapsa.insurance.crm.rows;

import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.policy.Policy;

import tech.lapsa.insurance.crm.beans.i.RequestType;

public class CallbackRequestRowDataModel extends InsuranceRequestRowDataModel<CallbackRequest>
	implements RequestRow<CallbackRequest> {

    public CallbackRequestRowDataModel(CallbackRequest entity) {
	super(entity);
    }

    @Override
    public RequestType getType() {
	return RequestType.CALLBACK_REQUEST;
    }

    @Override
    public Policy getPolicy() {
	return null;
    }

    @Override
    public Casco getCasco() {
	return null;
    }
}
