package tech.lapsa.insurance.crm.beans.actions;

import javax.faces.FacesException;

public interface ActionCDIBean {
    String doAction() throws FacesException, IllegalStateException, IllegalArgumentException;
}
