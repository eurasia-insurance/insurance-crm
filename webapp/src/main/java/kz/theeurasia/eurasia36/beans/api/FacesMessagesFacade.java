package kz.theeurasia.eurasia36.beans.api;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import kz.theeurasia.eurasia36.application.UIMessages;

public interface FacesMessagesFacade {

    FacesMessage addMessage(UIMessages message);

    FacesMessage addMessage(UIMessages message, FacesMessage.Severity severity);

    FacesMessage addMessage(UIMessages message, FacesMessage.Severity severity, String clientId);

    FacesMessage addMessage(UIMessages message, UIMessages details, FacesMessage.Severity severity,
	    String clientId);

    FacesMessage createMessage(UIMessages message);

    FacesMessage createMessage(UIMessages message, FacesMessage.Severity severity);

    FacesMessage createMessage(UIMessages message, FacesMessage.Severity severity, String clientId);

    FacesMessage createMessage(UIMessages message, UIMessages details, FacesMessage.Severity severity,
	    String clientId);

    ValidatorException throwValidationException(UIMessages message);

    ValidatorException throwValidationException(UIMessages message, FacesMessage.Severity severity);

    ValidatorException throwValidationException(UIMessages message, FacesMessage.Severity severity, String clientId);

    ValidatorException throwValidationException(UIMessages messagee, UIMessages details,
	    FacesMessage.Severity severity, String clientId);

    FacesMessage addExceptionMessage(UIMessages message, Throwable e);

    FacesMessage addExceptionMessage(Throwable e);
}
