package tech.lapsa.insurance.crm.rows;

import java.time.Instant;
import java.util.Currency;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.elements.InsuranceRequestStatus;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.InsuranceRequestCancellationReason;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public abstract class InsuranceRequestRowDataModel<T extends InsuranceRequest> extends RequestRowDataModel<T>
	implements RequestRow<T> {

    public InsuranceRequestRowDataModel(T entity) {
	super(entity);
    }

    @Override
    public InsuranceRequestStatus getInsuranceRequestStatus() {
	try {
	    return entity.getInsuranceRequestStatus();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public InsuranceRequestCancellationReason getInsuranceRequestCancellationReason() {
	try {
	    return entity.getInsuranceRequestCancellationReason();
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
	    return entity.getPayment().getReference();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentCard() {
	try {
	    return entity.getPayment().getCard();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentCardBank() {
	try {
	    return entity.getPayment().getCardBank();
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
	    return entity.getPayment().getInstant();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentPayerName() {
	try {
	    return entity.getPayment().getPayerName();
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

    @Override
    public String getComments() {
	return null;
    }

    @Override
    public Casco getCasco() {
	return null;
    }

    @Override
    public String getInvoiceNumber() {
	try {
	    return entity.getPayment().getInvoiceNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }


    @Override
    public Double getInvoiceAmount() {
	try {
	    return entity.getPayment().getInvoiceAmount();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getInvoicePayeeName() {
	try {
	    return entity.getPayment().getInvoicePayeeName();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getInvoicePayeeEmail() {
	try {
	    return entity.getPayment().getInvoicePayeeEmail();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public PhoneNumber getInvoicePayeePhone() {
	try {
	    return entity.getPayment().getInvoicePayeePhone();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public TaxpayerNumber getInvoicePayeeTaxpayerNumber() {
	try {
	    return entity.getPayment().getInvoicePayeeTaxpayerNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public LocalizationLanguage getInvoiceLanguage() {
	try {
	    return entity.getPayment().getInvoiceLanguage();
	} catch (NullPointerException e) {
	    return null;
	}
    }
}
