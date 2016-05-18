package kz.theeurasia.eurasia36.beans.request;

import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.ResourceBundleProducer;

@RequestScoped
public class DefaultResourceBundleProducer implements ResourceBundleProducer {

    @Override
    public ResourceBundle getUIMessages() {
	FacesContext context = FacesContext.getCurrentInstance();
	return context.getApplication().getResourceBundle(context, UIMessages.BUNDLE_VAR);
    }
}
