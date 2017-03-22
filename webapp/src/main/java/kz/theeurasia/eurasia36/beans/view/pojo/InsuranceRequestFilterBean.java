package kz.theeurasia.eurasia36.beans.view.pojo;

import java.io.Serializable;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.TransactionProblem;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.dao.filter.InsuranceRequestFilter;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;

public class InsuranceRequestFilterBean implements InsuranceRequestFilter, Serializable {
    private static final long serialVersionUID = -8470601310057087780L;

    private InsuranceRequestType insuranceRequestType;
    private InsuranceProductType insuranceProductType;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private ObtainingMethod obtainingMethod;
    private ObtainingStatus obtainingStatus;
    private TransactionStatus transactionStatus;
    private TransactionProblem transactionProblem;

    @Override
    public InsuranceRequestType getRequestType() {
	return insuranceRequestType;
    }

    public void setRequestType(InsuranceRequestType insuranceRequestType) {
	this.insuranceRequestType = insuranceRequestType;
    }

    @Override
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

}
