package kz.theeurasia.eurasia36.beans.api;

import java.util.Date;

import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.RequestType;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

import kz.theeurasia.eurasia36.beans.view.pojo.DefaultInsuranceRequestFitler;

public interface InsuranceRequestsFilterHolder extends WritableValueHolder<DefaultInsuranceRequestFitler> {

    ObtainingStatus getObtainingStatus();

    void setObtainingStatus(ObtainingStatus obtainingStatus);

    boolean isAdvanced();

    void setAdvanced(boolean advanced);

    boolean isAutoRefresh();

    void setAutoRefresh(boolean autoRefresh);

    int getAutoRefreshInterval();

    void setAutoRefreshInterval(int autoRefreshInterval);

    RequestStatus getRequestStatus();

    void setRequestStatus(RequestStatus requestStatus);

    RequestType getRequestType();

    void setRequestType(RequestType requestType);

    InsuranceProductType getInsuranceProductType();

    void setInsuranceProductType(InsuranceProductType insuranceProductType);

    PaymentMethod getPaymentMethod();

    void setPaymentMethod(PaymentMethod paymentMethod);

    PaymentStatus getPaymentStatus();

    void setPaymentStatus(PaymentStatus paymentStatus);

    ObtainingMethod getObtainingMethod();

    void setObtainingMethod(ObtainingMethod obtainingMethod);

    ProgressStatus getProgressStatus();

    void setProgressStatus(ProgressStatus progressStatus);

    TransactionStatus getTransactionStatus();

    void setTransactionStatus(TransactionStatus transactionStatus);

    TransactionProblem getTransactionProblem();

    void setTransactionProblem(TransactionProblem transactionProblem);

    Date getCreatedAfter();

    void setCreatedAfter(Date createdAfter);

    Date getCreatedBefore();

    void setCreatedBefore(Date createdBefore);

    Date getCompletedAfter();

    void setCompletedAfter(Date completedFrom);

    Date getCompletedBefore();

    void setCompletedBefore(Date completedBefore);

    User getAcceptedBy();

    void setAcceptedBy(User acceptedBy);

    User getCompletedBy();

    void setCompletedBy(User completedBy);

    Integer getId();

    void setId(Integer id);

    String getRequesterNameMask();

    void setRequesterNameMask(String requesterNameMask);

    String getRequesterIDNumberMask();

    void setRequesterIDNumberMask(String requesterIDNumberMask);

}