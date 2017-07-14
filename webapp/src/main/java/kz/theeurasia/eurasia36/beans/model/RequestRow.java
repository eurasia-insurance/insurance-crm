package kz.theeurasia.eurasia36.beans.model;

import java.time.LocalDateTime;

import com.lapsa.fin.FinCurrency;
import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestSource;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.PaymentMethod;
import com.lapsa.localization.LocalizationLanguage;

import kz.theeurasia.eurasia36.beans.api.RequestType;

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

    LocalDateTime getCreated();

    User getCreatedBy();

    LocalDateTime getUpdated();

    LocalDateTime getAccepted();

    User getAcceptedBy();

    LocalDateTime getCompleted();

    User getCompletedBy();

    LocalDateTime getClosed();

    User getClosedBy();

    Double getAmount();

    FinCurrency getCurrency();

    PaymentMethod getPaymentMethod();

    PaymentStatus getPaymentStatus();

    String getPaymentReference();

    String getRequesterName();

    String getRequesterEmail();

    String getRequesterPhone();

    LocalizationLanguage getRequesterLanguage();

    String getRequesterIdNumber();

    String getUTMSource();

    String getUTMMedium();

    String getUTMCampaign();

    String getUTMContent();

    String getUTMTerm();

    String getAgreementNumber();

    String getNote();
}
