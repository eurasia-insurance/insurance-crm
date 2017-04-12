package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.elements.InsuranceProductType;

import kz.theeurasia.eurasia36.beans.api.RequestType;

public class CallbackRequestRowDataModel extends RequestRowDataModel<CallbackRequest>
	implements RequestRow<CallbackRequest> {

    public CallbackRequestRowDataModel(CallbackRequest entity) {
	super(entity);
    }

    @Override
    public RequestType getType() {
	return RequestType.CALLBACK_REQUEST;
    }

    @Override
    public TransactionStatus getTransactionStatus() {
	return null;
    }

    @Override
    public TransactionProblem getTransactionProblem() {
	return null;
    }

    @Override
    public InsuranceProductType getInsuranceProductType() {
	return null;
    }

    @Override
    public InsuranceRequestType getInsuranceRequestType() {
	return null;
    }

    @Override
    public Double getAmount() {
	return 0d;
    }
}
