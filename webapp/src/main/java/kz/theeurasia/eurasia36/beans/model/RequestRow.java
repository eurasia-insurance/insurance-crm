package kz.theeurasia.eurasia36.beans.model;

import java.util.Date;
import java.util.List;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.CascoVehicle;
import com.lapsa.insurance.elements.InsuranceProductType;

public interface RequestRow<T extends Request> {

    T getEntity();

    Integer getId();

    ProgressStatus getProgressStatus();

    TransactionStatus getTransactionStatus();

    InsuranceProductType insuranceProductType();

    InsuranceRequestType insuranceRequestType();

    Date getCreated();

    Date getUpdated();

    Double getAmount();

    String getRequesterName();

    String getRequesterEmail();

    String getRequesterPhone();

    String getRequesterIdNumber();

    String getUTMSource();

    String getUTMTerm();

    List<CascoVehicle> getCascoVehiclesAsList();

}
