package tech.lapsa.insurance.crm.beans.rows;

import java.time.Instant;

import com.lapsa.fin.FinCurrency;
import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

import tech.lapsa.insurance.crm.beans.i.RequestType;

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

    @Override
    public String getPaymentMethodName() {
	return null;
    }

    @Override
    public Instant getPaymentInstant() {
	return null;
    }
}
