package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;

@Named("rqst")
@ViewScoped
public class RequestHolderBean extends AWritableValueHolder<List<RequestRow<?>>>
	implements Serializable, RequestHolder {

    private static final long serialVersionUID = 1L;

    @Override
    public void reset() {
	this.value = null;
    }

    public void setSingleRow(RequestRow<?> row) {
	this.value = Arrays.asList(row);
    }
}