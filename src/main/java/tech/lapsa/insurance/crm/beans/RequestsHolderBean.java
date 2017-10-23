package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import tech.lapsa.insurance.crm.beans.i.RequestsHolder;
import tech.lapsa.insurance.crm.beans.rows.RequestList;

@Named("rqsts")
@ViewScoped
public class RequestsHolderBean extends AWritableValueHolder<RequestList>
	implements Serializable, RequestsHolder {
    private static final long serialVersionUID = 7249376610273191727L;

    @Override
    public void reset() {
	this.value = null;
    }
}
