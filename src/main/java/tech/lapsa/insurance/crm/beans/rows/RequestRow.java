package tech.lapsa.insurance.crm.beans.rows;

import java.time.Instant;

import com.lapsa.fin.FinCurrency;
import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.ObtainingData;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.crm.UTMData;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.domain.policy.Policy;
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

public interface RequestRow<T extends Request> {

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

    FinCurrency getCurrency();

    PaymentStatus getPaymentStatus();

    String getPaymentReference();

    String getPaymentMethodName();

    Instant getPaymentInstant();

    String getRequesterName();

    String getRequesterEmail();

    String getRequesterPhone();

    RequesterData getRequester();

    LocalizationLanguage getRequesterLanguage();

    String getRequesterIdNumber();

    String getUTMSource();

    String getUTMMedium();

    String getUTMCampaign();

    String getUTMContent();

    String getUTMTerm();

    UTMData getUtm();

    String getAgreementNumber();

    String getNote();

    PaymentData getPayment();

    ObtainingData getObtaining();

    CalculationData getCalculation();

    Policy getPolicy();

    Casco getCasco();
}
