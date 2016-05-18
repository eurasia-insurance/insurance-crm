package kz.theeurasia.eurasia36.beans.application;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.api.ResourceBundleProducer;

@ApplicationScoped
public class DefaultFacesMessagesFacade implements FacesMessagesFacade {

    @Inject
    private ResourceBundleProducer resourceBundleProducer;

    @Override
    public FacesMessage addMessage(UIMessages message) {
	return addMessage(message, null, FacesMessage.SEVERITY_WARN, null);
    }

    @Override
    public FacesMessage addMessage(UIMessages message, FacesMessage.Severity severity) {
	return addMessage(message, null, severity, null);
    }

    @Override
    public FacesMessage addMessage(UIMessages message, Severity severity, String clientId) {
	return addMessage(message, null, severity, clientId);
    }

    @Override
    public FacesMessage addMessage(UIMessages messageKey, UIMessages detailsKey, FacesMessage.Severity severity,
	    String clientId) {
	ResourceBundle ui = resourceBundleProducer.getUIMessages();
	String message = null;
	if (messageKey != null)
	    message = ui.getString(messageKey.getKey());
	String details = null;
	if (detailsKey != null)
	    details = ui.getString(detailsKey.getKey());
	FacesMessage facesMessage = new FacesMessage(severity, message, details);
	FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	return facesMessage;
    }

}
