package kz.theeurasia.eurasia36.beans.model;

import java.util.Date;
import java.util.List;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.CascoVehicle;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceProductType;

public interface RequestRow<T extends Request> {

    T getEntity();

    // request properties

    Integer getId();

    RequestStatus getRequestStatus();

    ProgressStatus getProgressStatus();

    TransactionStatus getTransactionStatus();

    TransactionProblem getTransactionProblem();

    InsuranceProductType getInsuranceProductType();

    InsuranceRequestType getInsuranceRequestType();

    Date getCreated();

    Date getUpdated();

    Date getAccepted();

    User getAcceptedBy();

    Date getCompleted();

    User getCompletedBy();

    Date getClosed();

    User getClosedBy();

    Double getAmount();

    String getRequesterName();

    String getRequesterEmail();

    String getRequesterPhone();

    String getRequesterIdNumber();

    String getUTMSource();

    String getUTMMedium();

    String getUTMCampaign();

    String getUTMContent();

    String getUTMTerm();

    String getNote();

    List<CascoVehicle> getCascoVehiclesAsList();

}
