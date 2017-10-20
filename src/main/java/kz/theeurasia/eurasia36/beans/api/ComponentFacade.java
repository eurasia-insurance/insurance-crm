package kz.theeurasia.eurasia36.beans.api;

import javax.faces.component.UIInput;

public interface ComponentFacade {
    String messagesFor(UIInput component);

    String validationErrorsFor(UIInput component);
}
