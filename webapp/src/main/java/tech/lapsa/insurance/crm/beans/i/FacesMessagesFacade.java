package tech.lapsa.insurance.crm.beans.i;

import javax.faces.application.FacesMessage;

public interface FacesMessagesFacade {
    FacesMessage addExceptionMessage(Throwable e);
}
