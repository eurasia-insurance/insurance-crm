package tech.lapsa.insurance.crm.beans.rows;

import java.time.Instant;

import com.lapsa.fin.FinCurrency;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

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
    public FinCurrency getCurrency() {
	try {
	    return entity.getProduct().getCalculation().getPremiumCurrency();
	} catch (NullPointerException e) {
	    return null;
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
    public String getPaymentReference() {
	try {
	    return entity.getPayment().getPaymentReference();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentMethodName() {
	try {
	    return entity.getPayment().getMethodName();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Instant getPaymentInstant() {
	try {
	    return entity.getPayment().getPaymentInstant();
	} catch (NullPointerException e) {
	    return null;
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
