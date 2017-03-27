package kz.theeurasia.eurasia36.beans.model;

import java.util.List;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.casco.CascoVehicle;
import com.lapsa.insurance.elements.InsuranceProductType;

public class CallbackRequestRowDataModel extends RequestRowDataModel<CallbackRequest>
	implements RequestRow<CallbackRequest> {

    public CallbackRequestRowDataModel(CallbackRequest entity) {
	super(entity);
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

    @Override
    public List<CascoVehicle> getCascoVehiclesAsList() {
	return null;
    }

}
