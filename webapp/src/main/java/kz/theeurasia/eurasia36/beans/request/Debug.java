package kz.theeurasia.eurasia36.beans.request;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Debug {
    private static final boolean DEBUG = false;
    
    public boolean isDebug() {
	return DEBUG;
    }

    public String getRandomTag() {
	return String.format("%1$03d", Math.round(Math.random() * 999));
    }
}
