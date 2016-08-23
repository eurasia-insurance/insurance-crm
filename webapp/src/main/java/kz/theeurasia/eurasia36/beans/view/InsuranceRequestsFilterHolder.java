package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.crm.ClosingResult;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

import kz.theeurasia.eurasia36.beans.api.WritableValueHolder;

@Named("insuranceRequestsFilter")
@ViewScoped
public class InsuranceRequestsFilterHolder extends DefaultWritableValueHolder<DefaultInsuranceRequestFitler>
	implements Serializable, WritableValueHolder<DefaultInsuranceRequestFitler> {

    private static final long serialVersionUID = -6980458753963030228L;

    private boolean advanced = false;

    // GENERATED

    public boolean isAdvanced() {
	return advanced;
    }

    public void setAdvanced(boolean advanced) {
	this.advanced = advanced;
    }

    // DELEGATE

    public RequestStatus getRequestStatus() {
	return value.getRequestStatus();
    }

    public void setRequestStatus(RequestStatus requestStatus) {
	value.setRequestStatus(requestStatus);
    }

    public InsuranceProductType getInsuranceProductType() {
	return value.getInsuranceProductType();
    }

    public void setInsuranceProductType(InsuranceProductType insuranceProductType) {
	value.setInsuranceProductType(insuranceProductType);
    }

    public PaymentMethod getPaymentMethod() {
	return value.getPaymentMethod();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
	value.setPaymentMethod(paymentMethod);
    }

    public PaymentStatus getPaymentStatus() {
	return value.getPaymentStatus();
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
	value.setPaymentStatus(paymentStatus);
    }

    public ObtainingMethod getObtainingMethod() {
	return value.getObtainingMethod();
    }

    public void setObtainingMethod(ObtainingMethod obtainingMethod) {
	value.setObtainingMethod(obtainingMethod);
    }

    public ClosingResult getClosingResult() {
	return value.getClosingResult();
    }

    public void setClosingResult(ClosingResult closingResult) {
	value.setClosingResult(closingResult);
    }
}
