package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.beans.rows.RequestRow;

@Named("rqst")
@ViewScoped
public class RequestHolderBean extends AWritableValueHolder<RequestRow<?>>
	implements Serializable, RequestHolder {
    private static final long serialVersionUID = -2574434730269891652L;

    @Override
    public void reset() {
	this.value = null;
    }
}