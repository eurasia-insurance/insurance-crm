package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.crm.ClosingResult;
import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.RequestType;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsFilterHolder;
import kz.theeurasia.eurasia36.beans.view.pojo.DefaultInsuranceRequestFitler;

@Named("insuranceRequestsFilter")
@ViewScoped
public class DefaultInsuranceRequestsFilterHolder extends DefaultWritableValueHolder<DefaultInsuranceRequestFitler>
	implements Serializable, InsuranceRequestsFilterHolder {

    private static final long serialVersionUID = -6980458753963030228L;

    private boolean advanced = false;

    @Override
    public void reset() {
	this.value = new DefaultInsuranceRequestFitler();
    }

    // GENERATED

    @Override
    public boolean isAdvanced() {
	return advanced;
    }

    @Override
    public void setAdvanced(boolean advanced) {
	this.advanced = advanced;
    }

    // DELEGATE

    @Override
    public ObtainingStatus getObtainingStatus() {
	return value.getObtainingStatus();
    }

    @Override
    public void setObtainingStatus(ObtainingStatus obtainingStatus) {
	value.setObtainingStatus(obtainingStatus);
    }

    @Override
    public RequestStatus getRequestStatus() {
	return value.getRequestStatus();
    }

    @Override
    public void setRequestStatus(RequestStatus requestStatus) {
	value.setRequestStatus(requestStatus);
    }

    @Override
    public RequestType getRequestType() {
	return value.getRequestType();
    }

    @Override
    public void setRequestType(RequestType requestType) {
	value.setRequestType(requestType);
    }

    @Override
    public InsuranceProductType getInsuranceProductType() {
	return value.getInsuranceProductType();
    }

    @Override
    public void setInsuranceProductType(InsuranceProductType insuranceProductType) {
	value.setInsuranceProductType(insuranceProductType);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
	return value.getPaymentMethod();
    }

    @Override
    public void setPaymentMethod(PaymentMethod paymentMethod) {
	value.setPaymentMethod(paymentMethod);
    }

    @Override
    public PaymentStatus getPaymentStatus() {
	return value.getPaymentStatus();
    }

    @Override
    public void setPaymentStatus(PaymentStatus paymentStatus) {
	value.setPaymentStatus(paymentStatus);
    }

    @Override
    public ObtainingMethod getObtainingMethod() {
	return value.getObtainingMethod();
    }

    @Override
    public void setObtainingMethod(ObtainingMethod obtainingMethod) {
	value.setObtainingMethod(obtainingMethod);
    }

    @Override
    public ClosingResult getClosingResult() {
	return value.getClosingResult();
    }

    @Override
    public void setClosingResult(ClosingResult closingResult) {
	value.setClosingResult(closingResult);
    }
}
