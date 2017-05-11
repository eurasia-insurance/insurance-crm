package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.PaymentMethod;

public abstract class InsuranceRequestRowDataModel<T extends InsuranceRequest> extends RequestRowDataModel<T>
	implements RequestRow<T> {

    public InsuranceRequestRowDataModel(T entity) {
	super(entity);
    }

    @Override
    public TransactionStatus getTransactionStatus() {
	try {
	    return entity.getTransactionStatus();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public TransactionProblem getTransactionProblem() {
	try {
	    return entity.getTransactionProblem();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public InsuranceProductType getInsuranceProductType() {
	try {
	    return entity.getProductType();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public InsuranceRequestType getInsuranceRequestType() {
	try {
	    return entity.getType();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Double getAmount() {
	try {
	    return entity.getProduct().getCalculation().getPremiumCost();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public PaymentMethod getPaymentMethod() {
	try {
	    return entity.getPayment().getMethod();
	} catch (NullPointerException e) {
	    return PaymentMethod.UNDEFINED;
	}
    }

    @Override
    public PaymentStatus getPaymentStatus() {
	try {
	    return entity.getPayment().getStatus();
	} catch (NullPointerException e) {
	    return PaymentStatus.UNDEFINED;
	}
    }

    @Override
    public String getAgreementNumber() {
	try {
	    return entity.getAgreementNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }

}
