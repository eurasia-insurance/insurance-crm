package kz.theeurasia.eurasia36.beans.api;

import javax.faces.application.FacesMessage;

import kz.theeurasia.eurasia36.application.UIMessages;

public interface FacesMessagesFacade {

    FacesMessage addMessage(UIMessages message);

    FacesMessage addMessage(UIMessages message, FacesMessage.Severity severity);

    FacesMessage addMessage(UIMessages message, FacesMessage.Severity severity, String clientId);

    FacesMessage addMessage(UIMessages messageKey, UIMessages detailsKey, FacesMessage.Severity severity,
	    String clientId);

}
