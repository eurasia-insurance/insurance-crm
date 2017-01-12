package kz.theeurasia.eurasia36.beans.view.pojo;

import java.util.Date;

import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.RequestType;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.dao.filter.InsuranceRequestFitler;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

public class DefaultInsuranceRequestFitler implements InsuranceRequestFitler {

    private Integer id;
    private String requesterNameMask;
    private String requesterIDNumberMask;

    private RequestStatus requestStatus;
    private RequestType requestType;
    private InsuranceProductType insuranceProductType;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private ObtainingMethod obtainingMethod;
    private ObtainingStatus obtainingStatus;
    private ProgressStatus progressStatus;
    private TransactionStatus transactionStatus;
    private TransactionProblem transactionProblem;

    private Date createdAfter;
    private Date createdBefore;
    private Date completedAfter;
    private Date completedBefore;

    private User acceptedBy;
    private User completedBy;

    @Override
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Override
    public String getRequesterNameMask() {
	return requesterNameMask;
    }

    public void setRequesterNameMask(String requesterNameMask) {
	this.requesterNameMask = requesterNameMask;
    }

    @Override
    public String getRequesterIDNumberMask() {
	return requesterIDNumberMask;
    }

    public void setRequesterIDNumberMask(String requesterIDNumberMask) {
	this.requesterIDNumberMask = requesterIDNumberMask;
    }

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
    public ProgressStatus getProgressStatus() {
	return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
	this.progressStatus = progressStatus;
    }

    @Override
    public TransactionStatus getTransactionStatus() {
	return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
	this.transactionStatus = transactionStatus;
    }

    @Override
    public TransactionProblem getTransactionProblem() {
	return transactionProblem;
    }

    public void setTransactionProblem(TransactionProblem transactionProblem) {
	this.transactionProblem = transactionProblem;
    }

    @Override
    public Date getCreatedAfter() {
	return createdAfter;
    }

    public void setCreatedAfter(Date createdAfter) {
	this.createdAfter = createdAfter;
    }

    @Override
    public Date getCreatedBefore() {
	return createdBefore;
    }

    public void setCreatedBefore(Date createdBefore) {
	this.createdBefore = createdBefore;
    }

    @Override
    public Date getCompletedAfter() {
	return completedAfter;
    }

    public void setCompletedAfter(Date completedAfter) {
	this.completedAfter = completedAfter;
    }

    @Override
    public Date getCompletedBefore() {
	return completedBefore;
    }

    public void setCompletedBefore(Date completedBefore) {
	this.completedBefore = completedBefore;
    }

    @Override
    public User getAcceptedBy() {
	return acceptedBy;
    }

    public void setAcceptedBy(User acceptedBy) {
	this.acceptedBy = acceptedBy;
    }

    @Override
    public User getCompletedBy() {
	return completedBy;
    }

    public void setCompletedBy(User completedBy) {
	this.completedBy = completedBy;
    }
}
