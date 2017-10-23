package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import kz.theeurasia.eurasia36.beans.api.RequestHolder;
import kz.theeurasia.eurasia36.beans.model.RequestRow;

@Named("rqst")
@ViewScoped
public class DefaultRequestHolder extends DefaultWritableValueHolder<RequestRow<?>>
	implements Serializable, RequestHolder {
    private static final long serialVersionUID = -2574434730269891652L;

    @Override
    public void reset() {
	this.value = null;
    }
}