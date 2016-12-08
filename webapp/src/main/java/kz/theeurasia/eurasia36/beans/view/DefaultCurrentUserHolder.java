package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.persistence.dao.EntityNotFound;
import com.lapsa.insurance.persistence.dao.UserDAO;

import kz.theeurasia.eurasia36.beans.api.CurrentManagerHolder;

@Named("currentManager")
@ViewScoped
public class DefaultCurrentManagerHolder extends DefaultWritableValueHolder<User>
	implements Serializable, CurrentManagerHolder {
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

	String name = null;

	if (name == null && principal != null)
	    name = principal.getName();

	if (name == null)
	    name = extContext.getRemoteUser();

	if (name == null)
	    name = req.getRemoteUser();

	if (name == null)
	    name = DEFAULT_REMOTE_USER;

	try {
	    value = userDAO.findByEmail(name);
	} catch (EntityNotFound e) {
	    logger.info(String.format("New User creating '%1$s'", name));
	    value = new User();
	    value.setEmail(name);
	    value = userDAO.save(value);
	}
    }

}
