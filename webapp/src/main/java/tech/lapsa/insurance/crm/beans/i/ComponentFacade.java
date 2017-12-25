package tech.lapsa.insurance.crm.beans.i;

import javax.faces.component.UIInput;

public interface ComponentFacade {
    String messagesFor(UIInput component);

    String validationErrorsFor(UIInput component);
}
