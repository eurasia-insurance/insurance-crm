package tech.lapsa.insurance.crm.rows;

import java.time.Instant;
import java.util.Currency;
import java.util.List;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.crm.UTMData;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.elements.InsuranceRequestStatus;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.InsuranceRequestCancellationReason;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public interface RequestRow<T extends InsuranceRequest> {

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

    boolean isArchived();

    default boolean isInbox() {
	return !isArchived();
    }

    ProgressStatus getProgressStatus();

    InsuranceRequestStatus getInsuranceRequestStatus();

    boolean isRequestCanceled();

    boolean isPolicyIssued();

    boolean isPremiumPaid();

    InsuranceRequestCancellationReason getInsuranceRequestCancellationReason();

    InsuranceProductType getInsuranceProductType();

    InsuranceRequestType getInsuranceRequestType();

    Instant getCreated();

    User getCreatedBy();

    Instant getUpdated();

    Instant getPicked();

    User getPickedBy();

    Instant getCompleted();

    User getCompletedBy();

    Double getAmount();

    Currency getCurrency();

    String getComments();

    // calculated

    Double getCalculatedAmount();

    Currency getCalculatedCurrency();

    // payment

    PaymentStatus getPaymentStatus();

    String getInvoiceNumber();

    Double getPaymentAmount();

    Currency getPaymentCurrency();

    String getPaymentReference();

    String getPaymentCard();

    String getPaymentCardBank();

    String getPaymentMethodName();

    Instant getPaymentInstant();

    String getPaymentPayerName();

    Double getInvoiceAmount();

    String getInvoicePayeeName();

    String getInvoicePayeeEmail();

    PhoneNumber getInvoicePayeePhone();

    TaxpayerNumber getInvoicePayeeTaxpayerNumber();

    LocalizationLanguage getInvoiceLanguage();

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

    // checkers

    default boolean isCanView() {
	return true;
    }

    default boolean isCanPick() {
	return isInbox()
		&& ProgressStatus.NEW.equals(getProgressStatus());
    }

    default boolean isCanAccept() {
	return ProgressStatus.ON_PROCESS.equals(getProgressStatus())
		&& PaymentStatus.UNDEFINED.equals(getPaymentStatus())
		&& getEntity() instanceof InsuranceRequest;
    }

    default boolean isCanPause() {
	return isInbox()
		&& ProgressStatus.ON_PROCESS.equals(getProgressStatus());
    }

    default boolean isCanResume() {
	return isInbox()
		&& ProgressStatus.ON_HOLD.equals(getProgressStatus());
    }

    default boolean isCanComplete() {
	return isInbox()
		&& !ProgressStatus.FINISHED.equals(getProgressStatus());
    }

    default boolean isCanComment() {
	return true;
    }

    default boolean isCanUncomplete() {
	return isInbox()
		&& !ProgressStatus.FINISHED.equals(getProgressStatus())
		&& !PaymentStatus.DONE.equals(getPaymentStatus());
    }

    default boolean isCanArchive() {
	return isInbox()
		&& ProgressStatus.FINISHED.equals(getProgressStatus());
    }

    default boolean isCanDelete() {
	return !InsuranceRequestStatus.REQUEST_CANCELED.equals(getInsuranceRequestStatus());
    }
}
