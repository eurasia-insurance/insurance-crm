package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

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

import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsFilterHolder;
import kz.theeurasia.eurasia36.beans.view.pojo.DefaultInsuranceRequestFitler;

@Named("insuranceRequestsFilter")
@ViewScoped
public class DefaultInsuranceRequestsFilterHolder extends DefaultWritableValueHolder<DefaultInsuranceRequestFitler>
	implements Serializable, InsuranceRequestsFilterHolder {

    private static final long serialVersionUID = -6980458753963030228L;

    private boolean advanced = false;
    private boolean autoRefresh = false;
    private int autoRefreshInterval = 30;

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

    @Override
    public boolean isAutoRefresh() {
	return autoRefresh;
    }

    @Override
    public void setAutoRefresh(boolean autoRefresh) {
	this.autoRefresh = autoRefresh;
    }

    @Override
    public int getAutoRefreshInterval() {
	return autoRefreshInterval;
    }

    @Override
    public void setAutoRefreshInterval(int autoRefreshInterval) {
	this.autoRefreshInterval = autoRefreshInterval;
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
    public ProgressStatus getProgressStatus() {
	return value.getProgressStatus();
    }

    @Override
    public void setProgressStatus(ProgressStatus progressStatus) {
	value.setProgressStatus(progressStatus);
    }

    @Override
    public TransactionStatus getTransactionStatus() {
	return value.getTransactionStatus();
    }

    @Override
    public void setTransactionStatus(TransactionStatus transactionStatus) {
	value.setTransactionStatus(transactionStatus);
    }

    @Override
    public TransactionProblem getTransactionProblem() {
	return value.getTransactionProblem();
    }

    @Override
    public void setTransactionProblem(TransactionProblem transactionProblem) {
	value.setTransactionProblem(transactionProblem);
    }

    @Override
    public Date getCreatedAfter() {
	return value.getCreatedAfter();
    }

    @Override
    public void setCreatedAfter(Date createdAfter) {
	value.setCreatedAfter(createdAfter);
    }

    @Override
    public Date getCreatedBefore() {
	return value.getCreatedBefore();
    }

    @Override
    public void setCreatedBefore(Date createdBefore) {
	value.setCreatedBefore(createdBefore);
    }

    @Override
    public Date getCompletedAfter() {
	return value.getCompletedAfter();
    }

    @Override
    public void setCompletedAfter(Date completedAfter) {
	value.setCompletedAfter(completedAfter);
    }

    @Override
    public Date getCompletedBefore() {
	return value.getCompletedBefore();
    }

    @Override
    public void setCompletedBefore(Date completedBefore) {
	value.setCompletedBefore(completedBefore);
    }

    @Override
    public User getAcceptedBy() {
	return value.getAcceptedBy();
    }

    @Override
    public void setAcceptedBy(User acceptedBy) {
	value.setAcceptedBy(acceptedBy);
    }

    @Override
    public User getCompletedBy() {
	return value.getCompletedBy();
    }

    @Override
    public void setCompletedBy(User completedBy) {
	value.setCompletedBy(completedBy);
    }

}
