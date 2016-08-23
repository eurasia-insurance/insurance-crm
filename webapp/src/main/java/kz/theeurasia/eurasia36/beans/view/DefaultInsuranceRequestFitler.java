package kz.theeurasia.eurasia36.beans.view;

import com.lapsa.insurance.crm.ClosingResult;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;
import com.lapsa.insurance.persistence.dao.filter.InsuranceRequestFitler;

public class DefaultInsuranceRequestFitler implements InsuranceRequestFitler {

    private RequestStatus requestStatus;
    private InsuranceProductType insuranceProductType;
    private ClosingResult closingResult;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private ObtainingMethod obtainingMethod;

    @Override
    public RequestStatus getRequestStatus() {
	return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
	this.requestStatus = requestStatus;
    }

    public InsuranceProductType getInsuranceProductType() {
	return insuranceProductType;
    }

    public void setInsuranceProductType(InsuranceProductType insuranceProductType) {
	this.insuranceProductType = insuranceProductType;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
	return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
	this.paymentMethod = paymentMethod;
    }

    @Override
    public PaymentStatus getPaymentStatus() {
	return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
	this.paymentStatus = paymentStatus;
    }

    @Override
    public ObtainingMethod getObtainingMethod() {
	return obtainingMethod;
    }

    public void setObtainingMethod(ObtainingMethod obtainingMethod) {
	this.obtainingMethod = obtainingMethod;
    }

    @Override
    public ClosingResult getClosingResult() {
	return closingResult;
    }

    public void setClosingResult(ClosingResult closingResult) {
	this.closingResult = closingResult;
    }
}
