package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import kz.theeurasia.eurasia36.beans.api.RequestsHolder;
import kz.theeurasia.eurasia36.beans.model.RequestList;

@Named("rqsts")
@ViewScoped
public class DefaultRequestsHolder extends DefaultWritableValueHolder<RequestList>
	implements Serializable, RequestsHolder {
    private static final long serialVersionUID = 7249376610273191727L;

    @Override
    public void reset() {
	this.value = null;
    }
}
