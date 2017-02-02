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

import com.lapsa.insurance.dao.EntityNotFound;
import com.lapsa.insurance.dao.UserDAO;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.domain.crm.UserLogin;

import kz.theeurasia.eurasia36.beans.api.CurrentUserHolder;
import kz.theeurasia.eurasia36.beans.application.SecurityRole;

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
    public boolean inRole(SecurityRole role) {
	return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role.getRoleName());
    }

    @Override
    public void reset() {
	ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
	HttpServletRequest req = (HttpServletRequest) extContext.getRequest();

	Principal userPrincipal = null;

	if (userPrincipal == null)
	    userPrincipal = extContext.getUserPrincipal();

	if (userPrincipal == null) {
	    userPrincipal = req.getUserPrincipal();
	}

	String principalName = null;

	if (principalName == null && userPrincipal != null)
	    principalName = userPrincipal.getName();

	if (principalName == null)
	    principalName = extContext.getRemoteUser();

	if (principalName == null)
	    principalName = req.getRemoteUser();

	if (principalName == null)
	    principalName = DEFAULT_REMOTE_USER;

	try {
	    value = userDAO.findByLogin(principalName);
	} catch (EntityNotFound e) {
	    logger.info(String.format("New User creating '%1$s'", principalName));

	    value = new User();
	    UserLogin login = value.addLogin(new UserLogin());
	    login.setName(principalName);

	    if (Util.isEmail(principalName)) {
		value.setEmail(principalName);
		value.setName(Util.stripEmailToName(principalName));
	    } else {
		value.setName(principalName);
	    }
	    value = userDAO.save(value);
	}
    }

}
