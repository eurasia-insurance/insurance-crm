package tech.lapsa.insurance.crm.rows;

import java.time.Instant;
import java.util.Currency;
import java.util.List;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.crm.UTMData;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.RequestSource;
import com.lapsa.insurance.elements.RequestStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;
import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyObjects;

public interface RequestRow<T extends Request> {

    public static RequestRow<?> from(final Request request) throws IllegalArgumentException {
	MyObjects.requireNonNull(request, "request");
	if (MyObjects.isA(request, PolicyRequest.class))
	    return new PolicyRequestRowDataModel(MyObjects.requireA(request, PolicyRequest.class));

	if (MyObjects.isA(request, CascoRequest.class))
	    return new CascoRequestRowDataModel(MyObjects.requireA(request, CascoRequest.class));

	if (MyObjects.isA(request, CallbackRequest.class))
	    return new CallbackRequestRowDataModel(MyObjects.requireA(request, CallbackRequest.class));

	throw new RuntimeException("Invalid type of request");
    }

    public static List<RequestRow<?>> from(final List<? extends Request> list) {
	MyObjects.requireNonNull(list, "list");
	return list.stream().map(RequestRow::from).collect(MyCollectors.unmodifiableList());
    }

    T getEntity();

    RequestType getType();

    Integer getId();

    RequestStatus getRequestStatus();

    RequestSource getRequestSource();

    ProgressStatus getProgressStatus();

    TransactionStatus getTransactionStatus();

    TransactionProblem getTransactionProblem();

    InsuranceProductType getInsuranceProductType();

    InsuranceRequestType getInsuranceRequestType();

    Instant getCreated();

    User getCreatedBy();

    Instant getUpdated();

    Instant getAccepted();

    User getAcceptedBy();

    Instant getCompleted();

    User getCompletedBy();

    Instant getClosed();

    User getClosedBy();

    Double getAmount();

    Currency getCurrency();

    // calculated

    Double getCalculatedAmount();

    Currency getCalculatedCurrency();

    // payment

    PaymentStatus getPaymentStatus();

    String getPaymentInvoiceNumber();

    Double getPaymentAmount();

    Currency getPaymentCurrency();

    String getPaymentReference();

    String getPaymentMethodName();

    Instant getPaymentInstant();

    // requester

    String getRequesterName();

    String getRequesterEmail();

    String getRequesterPhone();

    RequesterData getRequester();

    LocalizationLanguage getRequesterLanguage();

    String getRequesterIdNumber();

    // utm

    String getUTMSource();

    String getUTMMedium();

    String getUTMCampaign();

    String getUTMContent();

    String getUTMTerm();

    UTMData getUtm();

    // other

    String getAgreementNumber();

    String getNote();

    PaymentData getPayment();

    CalculationData getCalculation();

    Policy getPolicy();

    Casco getCasco();
}
