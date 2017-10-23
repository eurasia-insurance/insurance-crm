package tech.lapsa.insurance.crm.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("debug")
@RequestScoped
public class DebugBean {
    private static final boolean DEBUG = false;

    public boolean isDebug() {
	return DEBUG;
    }

    public String getRandomTag() {
	return String.format("%1$03d", Math.round(Math.random() * 999));
    }

    public void throwException() {
	throw new RuntimeException("Test web-page exception");
    }

    public void throwViewExpired() {
	throw new ViewExpiredException("A ViewExpiredException!",
		FacesContext.getCurrentInstance().getViewRoot().getViewId());
    }

    public void longAjaxRequest() {
	try {
	    Thread.sleep(10 * 1000);
	} catch (InterruptedException ignored) {
	}
    }
}
