package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.fin.FinCurrency;
import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentMethod;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

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

    @Override
    public FinCurrency getCurrency() {
	return null;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
	return PaymentMethod.UNDEFINED;
    }

    @Override
    public PaymentStatus getPaymentStatus() {
	return PaymentStatus.UNDEFINED;
    }

    @Override
    public String getPaymentReference() {
	return null;
    }

    @Override
    public String getAgreementNumber() {
	return null;
    }
}
