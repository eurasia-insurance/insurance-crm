package tech.lapsa.insurance.crm.beans;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import tech.lapsa.insurance.crm.beans.i.ComponentFacade;

@Named("componentFacade")
@ApplicationScoped
public class ComponentFacadeBean implements ComponentFacade {

    @Override
    public String messagesFor(UIInput component) {
	List<FacesMessage> messages = FacesContext.getCurrentInstance().getMessageList(component.getClientId());
	StringBuffer sb = new StringBuffer();
	for (FacesMessage message : messages) {
	    sb.append(message.getSummary());
	    sb.append(" ");
	}
	return sb.length() > 0 ? sb.toString() : null;
    }

    @Override
    public String validationErrorsFor(UIInput component) {
	if (!component.isValid())
	    return messagesFor(component);
	return null;
    }
}
