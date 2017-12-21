package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.time.Instant;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;

@Named("rqst")
@ViewScoped
public class RequestHolderBean extends AWritableValueHolder<RequestRow<?>>
	implements Serializable, RequestHolder {

    private static final long serialVersionUID = 1L;

    @Override
    public void reset() {
	this.value = null;
    }

    // paidAmount

    private Double paidAmount;

    @Override
    public Double getPaidAmount() {
	return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
	this.paidAmount = paidAmount;
    }

    // paidInstant

    private Instant paidInstant;

    @Override
    public Instant getPaidInstant() {
	return paidInstant;
    }

    public void setPaidInstant(Instant paidInstant) {
	this.paidInstant = paidInstant;
    }

    // paidReference

    private String paidReference;

    @Override
    public String getPaidReference() {
	return paidReference;
    }

    public void setPaidReference(String paidReference) {
	this.paidReference = paidReference;
    }
}