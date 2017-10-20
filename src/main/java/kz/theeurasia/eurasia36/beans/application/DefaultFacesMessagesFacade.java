package kz.theeurasia.eurasia36.beans.application;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@ApplicationScoped
public class DefaultFacesMessagesFacade implements FacesMessagesFacade {

    private FacesMessage addMessage(String message, Severity severity) {
	FacesMessage fm = createMessage(message, severity);
	FacesContext.getCurrentInstance().addMessage(null, fm);
	return fm;
    }

    private FacesMessage createMessage(String message, Severity severity) {
	return generateMessage(message, null, severity, null);
    }

    // PRIVATE

    private FacesMessage generateMessage(String message, String details, FacesMessage.Severity severity,
	    String clientId) {
	FacesMessage facesMessage = new FacesMessage(severity, message, details);
	return facesMessage;
    }

    @Override
    public FacesMessage addExceptionMessage(Throwable e) {
	return addMessage(e.getMessage(), FacesMessage.SEVERITY_FATAL);
    }
}
