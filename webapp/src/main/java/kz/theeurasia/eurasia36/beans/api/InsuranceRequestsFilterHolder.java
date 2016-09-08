package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.crm.ClosingResult;
import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

import kz.theeurasia.eurasia36.beans.view.pojo.DefaultInsuranceRequestFitler;

public interface InsuranceRequestsFilterHolder extends WritableValueHolder<DefaultInsuranceRequestFitler> {

    ObtainingStatus getObtainingStatus();

    void setObtainingStatus(ObtainingStatus obtainingStatus);

    boolean isAdvanced();

    void setAdvanced(boolean advanced);

    RequestStatus getRequestStatus();

    void setRequestStatus(RequestStatus requestStatus);

    InsuranceProductType getInsuranceProductType();

    void setInsuranceProductType(InsuranceProductType insuranceProductType);

    PaymentMethod getPaymentMethod();

    void setPaymentMethod(PaymentMethod paymentMethod);

    PaymentStatus getPaymentStatus();

    void setPaymentStatus(PaymentStatus paymentStatus);

    ObtainingMethod getObtainingMethod();

    void setObtainingMethod(ObtainingMethod obtainingMethod);

    ClosingResult getClosingResult();

    void setClosingResult(ClosingResult closingResult);

}