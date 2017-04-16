package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.security.InsuranceRole;
import com.lapsa.insurance.services.domain.UserFacade;
import com.lapsa.utils.security.SecurityUtils;

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
    public void reset() {
	Principal userPrincipal = SecurityUtils.getUserPrincipal();

	String principalName = null;

	if (principalName == null && userPrincipal != null)
	    principalName = userPrincipal.getName();

	if (principalName == null)
	    principalName = SecurityUtils.getRemoteUser();

	if (principalName == null)
	    principalName = DEFAULT_REMOTE_USER;

	value = userFacade.findOrCreate(principalName);
    }

    @Override
    public boolean isCanView() {
	return SecurityUtils.isInRole(InsuranceRole.ADMIN_ROLE, InsuranceRole.SPECIALIST_ROLE,
		InsuranceRole.AGENT_ROLE);
    }

    @Override
    public boolean isCanChange() {
	return SecurityUtils.isInRole(InsuranceRole.ADMIN_ROLE, InsuranceRole.SPECIALIST_ROLE);
    }

    @Override
    public boolean isCanClose() {
	return SecurityUtils.isInRole(InsuranceRole.ADMIN_ROLE);
    }

}
