package kz.theeurasia.eurasia36.beans.view.pojo;

import com.lapsa.insurance.crm.ClosingResult;
import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.RequestType;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;
import com.lapsa.insurance.persistence.dao.filter.InsuranceRequestFitler;

public class DefaultInsuranceRequestFitler implements InsuranceRequestFitler {

    private RequestStatus requestStatus;
    private RequestType requestType;
    private InsuranceProductType insuranceProductType;
    private ClosingResult closingResult;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private ObtainingMethod obtainingMethod;
    private ObtainingStatus obtainingStatus;

    @Override
    public RequestStatus getRequestStatus() {
	return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
	this.requestStatus = requestStatus;
    }

    @Override
    public RequestType getRequestType() {
	return requestType;
    }

    public void setRequestType(RequestType requestType) {
	this.requestType = requestType;
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
    public ObtainingStatus getObtainingStatus() {
	return obtainingStatus;
    }

    public void setObtainingStatus(ObtainingStatus obtainingStatus) {
	this.obtainingStatus = obtainingStatus;
    }

    @Override
    public ClosingResult getClosingResult() {
	return closingResult;
    }

    public void setClosingResult(ClosingResult closingResult) {
	this.closingResult = closingResult;
    }

}
