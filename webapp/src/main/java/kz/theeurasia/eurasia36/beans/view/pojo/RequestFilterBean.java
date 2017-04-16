package kz.theeurasia.eurasia36.beans.view.pojo;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.Date;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.dao.filter.RequestFilter;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

import kz.theeurasia.eurasia36.application.InsuranceRoleGroup;

public class RequestFilterBean implements RequestFilter, Serializable {
    private static final long serialVersionUID = -5052366661196023039L;

    // Request properties

    private Integer id;
    private String requesterNameMask;
    private String requesterIdNumberMask;

    private RequestStatus requestStatus;
    private ProgressStatus progressStatus;

    private Date createdAfter;
    private Date createdBefore;
    private Date completedAfter;
    private Date completedBefore;

    private User createdBy;
    private User acceptedBy;
    private User completedBy;
    private User closedBy;

    // InsuranceRequest properties

    private InsuranceRequestType insuranceRequestType;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private ObtainingMethod obtainingMethod;
    private ObtainingStatus obtainingStatus;
    private TransactionStatus transactionStatus;
    private TransactionProblem transactionProblem;

    public RequestFilterBean() {
    }

    public RequestFilterBean(User createdBy) {
	this.createdBy = createdBy;
    }

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
    public String getRequesterIdNumberMask() {
	return requesterIdNumberMask;
    }

    public void setRequesterIdNumberMask(String requesterIdNumberMask) {
	this.requesterIdNumberMask = requesterIdNumberMask;
    }

    @Override
    public RequestStatus getRequestStatus() {
	return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
	this.requestStatus = requestStatus;
    }

    @Override
    public ProgressStatus getProgressStatus() {
	return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
	this.progressStatus = progressStatus;
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

    @Override
    public InsuranceRequestType getRequestType() {
	return insuranceRequestType;
    }

    public void setRequestType(InsuranceRequestType insuranceRequestType) {
	this.insuranceRequestType = insuranceRequestType;
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
    public User getCreatedBy() {
	return createdBy;
    }

    @Override
    public User getClosedBy() {
	return closedBy;
    }

    public void setCreatedBy(User createdBy) {
	// тем кто в группе VIEWERS_OWNED_ONLY не разрешено менять настройку фильтра
	checkRoleDenied(InsuranceRoleGroup.VIEWERS_OWNED_ONLY);
	this.createdBy = createdBy;
    }

    public void setClosedBy(User closedBy) {
	this.closedBy = closedBy;
    }

}
