package kz.theeurasia.eurasia36.beans.api;

import javax.faces.application.FacesMessage;

public interface FacesMessagesFacade {
    FacesMessage addExceptionMessage(Throwable e);
}
