package tech.lapsa.insurance.crm.rows;

import java.time.Instant;
import java.util.Currency;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;

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
	final Double payment = getPaymentAmount();
	if (MyNumbers.positive(payment))
	    return payment;
	final Double calculated = getCalculatedAmount();
	if (MyNumbers.positive(calculated))
	    return calculated;
	return null;
    }

    @Override
    public Currency getCurrency() {
	final Currency payment = getPaymentCurrency();
	if (MyObjects.nonNull(payment))
	    return payment;
	final Currency calculated = getCalculatedCurrency();
	if (MyObjects.nonNull(calculated))
	    return calculated;
	return null;
    }

    @Override
    public Double getCalculatedAmount() {
	try {
	    return entity.getProduct().getCalculation().getAmount();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Currency getCalculatedCurrency() {
	try {
	    return entity.getProduct().getCalculation().getCurrency();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Double getPaymentAmount() {
	try {
	    return entity.getPayment().getAmount();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Currency getPaymentCurrency() {
	try {
	    return entity.getPayment().getCurrency();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentInvoiceNumber() {
	try {
	    return entity.getPayment().getInvoiceNumber();
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

    @Override
    public PaymentData getPayment() {
	try {
	    return entity.getPayment();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public CalculationData getCalculation() {
	try {
	    return entity.getProduct().getCalculation();
	} catch (NullPointerException e) {
	    return null;
	}
    }
}
