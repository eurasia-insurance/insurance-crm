package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.security.AuthorizationUtil;
import com.lapsa.insurance.security.RoleGroup;
import com.lapsa.insurance.services.domain.UserFacade;

import kz.theeurasia.eurasia36.beans.api.CurrentUserHolder;

@Named("currentUser")
@SessionScoped
public class DefaultCurrentUserHolder extends DefaultWritableValueHolder<User>
	implements Serializable, CurrentUserHolder {
    private static final long serialVersionUID = 3813022087120135731L;

    private static final String DEFAULT_REMOTE_USER = "Guest";

    @Inject
    private UserFacade userFacade;

    @PostConstruct
    public void init() {
	reset();
    }

    @Override
    public boolean inRoles(RoleGroup... roles) {
	return AuthorizationUtil.isInRole(roles);
    }

    @Override
    public boolean inRole(RoleGroup role1, RoleGroup role2, RoleGroup role3) {
	return AuthorizationUtil.isInRole(role1, role2, role3);
    }

    @Override
    public boolean inRole(RoleGroup role1, RoleGroup role2) {
	return AuthorizationUtil.isInRole(role1, role2);
    }

    @Override
    public boolean inRole(RoleGroup role1) {
	return AuthorizationUtil.isInRole(role1);
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

	value = userFacade.findOrCreate(principalName);
    }

}
