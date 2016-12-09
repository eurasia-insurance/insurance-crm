package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.persistence.dao.EntityNotFound;
import com.lapsa.insurance.persistence.dao.UserDAO;

import kz.theeurasia.eurasia36.beans.api.CurrentUserHolder;

@Named("currentUser")
@SessionScoped
public class DefaultCurrentUserHolder extends DefaultWritableValueHolder<User>
	implements Serializable, CurrentUserHolder {
    private static final long serialVersionUID = 3813022087120135731L;

    private static final String DEFAULT_REMOTE_USER = "Guest";

    @Inject
    private transient Logger logger;

    @Inject
    private UserDAO userDAO;

    @PostConstruct
    public void init() {
	reset();
    }

    @Override
    public void reset() {
	ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
	HttpServletRequest req = (HttpServletRequest) extContext.getRequest();

	Principal principal = null;

	if (principal == null)
	    principal = extContext.getUserPrincipal();

	if (principal == null) {
	    principal = req.getUserPrincipal();
	}

	String email = null;

	if (email == null && principal != null)
	    email = principal.getName();

	if (email == null)
	    email = extContext.getRemoteUser();

	if (email == null)
	    email = req.getRemoteUser();

	if (email == null)
	    email = DEFAULT_REMOTE_USER;

	try {
	    value = userDAO.findByLogin(email);
	} catch (EntityNotFound e) {
	    logger.info(String.format("New User creating '%1$s'", email));
	    value = new User();
	    value.setLogin(email);
	    value.setEmail(email);
	    value.setName(stripEmailToName(email));
	    value = userDAO.save(value);
	}
    }

    private static String stripEmailToName(String email) {
	if (email == null)
	    return null;
	String[] verbs = email.split("\\@")[0].split("[\\.\\s]");
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < verbs.length; i++) {
	    String verb = verbs[i];
	    if (verb.length() == 0)
		continue;
	    sb.append(Character.toUpperCase(verb.charAt(0)));
	    if (verb.length() > 1)
		sb.append(verb.substring(1));
	    if (i < verbs.length - 1)
		sb.append(" ");
	}
	return sb.toString();
    }

}
