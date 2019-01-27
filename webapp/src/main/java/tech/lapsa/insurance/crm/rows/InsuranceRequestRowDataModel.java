package tech.lapsa.insurance.crm.rows;

import java.time.Instant;
import java.util.Currency;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestCancellationReason;
import com.lapsa.insurance.elements.InsuranceRequestStatus;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
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
    public boolean insuranceRequestIn(InsuranceRequestStatus... statuses) {
	return MyOptionals.of(entity).filter(it -> it.insuranceRequestStatusIn(statuses)).isPresent();
    }

    @Override
    public boolean isPending() {
	return InsuranceRequestStatus.PENDING.equals(getInsuranceRequestStatus());
    }

    @Override
    public boolean isRequestCanceled() {
	return InsuranceRequestStatus.REQUEST_CANCELED.equals(getInsuranceRequestStatus());
    }

    @Override
    public boolean isPolicyIssued() {
	return InsuranceRequestStatus.POLICY_ISSUED.equals(getInsuranceRequestStatus());
    }

    @Override
    public boolean isPremiumPaid() {
	return InsuranceRequestStatus.PREMIUM_PAID.equals(getInsuranceRequestStatus());
    }

    @Override
    public boolean isPaymentCanceled() {
	return InsuranceRequestStatus.PAYMENT_CANCELED.equals(getInsuranceRequestStatus());
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
	    return entity.getAmount();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Currency getPaymentCurrency() {
	try {
	    return entity.getCurrency();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentReference() {
	try {
	    return entity.getReference();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentCard() {
	try {
	    return entity.getCard();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentCardBank() {
	try {
	    return entity.getCardBank();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentMethodName() {
	try {
	    return entity.getMethodName();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Instant getPaymentInstant() {
	try {
	    return entity.getInstant();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getPaymentPayerName() {
	try {
	    return entity.getPayerName();
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
	    return entity.getInvoiceNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Double getInvoiceAmount() {
	try {
	    return entity.getInvoiceAmount();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getInvoicePayeeName() {
	try {
	    return entity.getInvoicePayeeName();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getInvoicePayeeEmail() {
	try {
	    return entity.getInvoicePayeeEmail();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public PhoneNumber getInvoicePayeePhone() {
	try {
	    return entity.getInvoicePayeePhone();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public TaxpayerNumber getInvoicePayeeTaxpayerNumber() {
	try {
	    return entity.getInvoicePayeeTaxpayerNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public LocalizationLanguage getInvoiceLanguage() {
	try {
	    return entity.getInvoiceLanguage();
	} catch (NullPointerException e) {
	    return null;
	}
    }
}
