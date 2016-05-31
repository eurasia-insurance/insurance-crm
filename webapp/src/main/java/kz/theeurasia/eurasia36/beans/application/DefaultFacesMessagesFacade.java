package kz.theeurasia.eurasia36.beans.application;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
	FacesMessage fm = createMessage(message);
	FacesContext.getCurrentInstance().addMessage(null, fm);
	return fm;
    }

    @Override
    public FacesMessage addMessage(UIMessages message, FacesMessage.Severity severity) {
	FacesMessage fm = createMessage(message, severity);
	FacesContext.getCurrentInstance().addMessage(null, fm);
	return fm;
    }

    @Override
    public FacesMessage addMessage(UIMessages message, Severity severity, String clientId) {
	FacesMessage fm = createMessage(message, severity, clientId);
	FacesContext.getCurrentInstance().addMessage(clientId, fm);
	return fm;
    }

    @Override
    public FacesMessage addMessage(UIMessages message, UIMessages details, FacesMessage.Severity severity,
	    String clientId) {
	FacesMessage fm = createMessage(message, details, severity, clientId);
	FacesContext.getCurrentInstance().addMessage(clientId, fm);
	return fm;
    }

    @Override
    public FacesMessage createMessage(UIMessages message) {
	return generateMessage(message, null, FacesMessage.SEVERITY_WARN, null);
    }

    @Override
    public FacesMessage createMessage(UIMessages message, Severity severity) {
	return generateMessage(message, null, severity, null);
    }

    @Override
    public FacesMessage createMessage(UIMessages message, Severity severity, String clientId) {
	return generateMessage(message, null, severity, clientId);
    }

    @Override
    public FacesMessage createMessage(UIMessages message, UIMessages details, Severity severity,
	    String clientId) {
	return generateMessage(message, details, severity, clientId);
    }

    @Override
    public ValidatorException throwValidationException(UIMessages message) {
	return new ValidatorException(createMessage(message, FacesMessage.SEVERITY_ERROR));
    }

    @Override
    public ValidatorException throwValidationException(UIMessages message, Severity severity) {
	return new ValidatorException(createMessage(message, severity));
    }

    @Override
    public ValidatorException throwValidationException(UIMessages message, Severity severity, String clientId) {
	return new ValidatorException(createMessage(message, severity, clientId));
    }

    @Override
    public ValidatorException throwValidationException(UIMessages message, UIMessages details, Severity severity,
	    String clientId) {
	return new ValidatorException(createMessage(message, details, severity, clientId));
    }

    // PRIVATE

    private FacesMessage generateMessage(UIMessages messageKey, UIMessages detailsKey, FacesMessage.Severity severity,
	    String clientId) {
	ResourceBundle ui = resourceBundleProducer.getUIMessages();
	String message = null;
	if (messageKey != null)
	    message = ui.getString(messageKey.getKey());
	String details = null;
	if (detailsKey != null)
	    details = ui.getString(detailsKey.getKey());
	FacesMessage facesMessage = new FacesMessage(severity, message, details);
	return facesMessage;
    }

}
