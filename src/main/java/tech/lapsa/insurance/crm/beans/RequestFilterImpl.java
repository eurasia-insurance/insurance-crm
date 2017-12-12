package tech.lapsa.insurance.crm.beans;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceRequestType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.ObtainingStatus;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.RequestSource;
import com.lapsa.insurance.elements.RequestStatus;
import com.lapsa.insurance.elements.TransactionProblem;
import com.lapsa.insurance.elements.TransactionStatus;

import tech.lapsa.insurance.dao.filter.RequestFilter;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class RequestFilterImpl implements RequestFilter {

    private static final long serialVersionUID = 1L;

    // Request properties

    private Integer id;
    private String requesterNameMask;

    private RequestStatus requestStatus;
    private RequestSource requestSource;
    private ProgressStatus progressStatus;

    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private LocalDateTime completedAfter;
    private LocalDateTime completedBefore;

    private User createdBy;
    private User acceptedBy;
    private User completedBy;
    private User closedBy;

    // InsuranceRequest properties

    private InsuranceRequestType insuranceRequestType;
    private PaymentStatus paymentStatus;
    private String paymentInvoiceNumber;
    private ObtainingMethod obtainingMethod;
    private ObtainingStatus obtainingStatus;
    private TransactionStatus transactionStatus;
    private String agreementNumberMask;
    private TransactionProblem transactionProblem;

    private TaxpayerNumber requesterTaxpayerNumber;

    public RequestFilterImpl() {
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
    public LocalDateTime getCreatedAfter() {
	return createdAfter;
    }

    public void setCreatedAfter(LocalDateTime createdAfter) {
	this.createdAfter = createdAfter;
    }

    @Override
    public LocalDateTime getCreatedBefore() {
	return createdBefore;
    }

    public void setCreatedBefore(LocalDateTime createdBefore) {
	this.createdBefore = createdBefore;
    }

    @Override
    public LocalDateTime getCompletedAfter() {
	return completedAfter;
    }

    public void setCompletedAfter(LocalDateTime completedAfter) {
	this.completedAfter = completedAfter;
    }

    @Override
    public LocalDateTime getCompletedBefore() {
	return completedBefore;
    }

    public void setCompletedBefore(LocalDateTime completedBefore) {
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
    public PaymentStatus getPaymentStatus() {
	return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
	this.paymentStatus = paymentStatus;
    }

    @Override
    public String getPaymentInvoiceNumber() {
	return paymentInvoiceNumber;
    }

    public void setPaymentInvoiceNumber(String paymentInvoiceNumber) {
	this.paymentInvoiceNumber = paymentInvoiceNumber;
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
    public String getAgreementNumberMask() {
	return agreementNumberMask;
    }

    public void setAgreementNumberMask(String agreementNumberMask) {
	this.agreementNumberMask = agreementNumberMask;
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
	this.createdBy = createdBy;
    }

    public void setClosedBy(User closedBy) {
	this.closedBy = closedBy;
    }

    @Override
    public RequestSource getRequestSource() {
	return requestSource;
    }

    public void setRequestSource(RequestSource requestSource) {
	this.requestSource = requestSource;
    }

    @Override
    public TaxpayerNumber getRequesterTaxpayerNumber() {
	return requesterTaxpayerNumber;
    }

    public void setRequesterTaxpayerNumber(TaxpayerNumber requesterTaxpayerNumber) {
	this.requesterTaxpayerNumber = requesterTaxpayerNumber;
    }

    @Override
    public ZoneId getZoneId() {
	return ZoneId.systemDefault();
    }
}
