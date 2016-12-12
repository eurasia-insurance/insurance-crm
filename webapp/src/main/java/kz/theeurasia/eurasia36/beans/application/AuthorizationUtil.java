package kz.theeurasia.eurasia36.beans.application;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class AuthorizationUtil {

    public static void checkRoleAllowed(String message, SecurityRole... rolesAllowed) {
	ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
	for (SecurityRole role : rolesAllowed)
	    if (ctx.isUserInRole(role.getRoleName()))
		return;
	throw new UnauthorizedException(message);
    }

    public static void checkRoleAllowed(SecurityRole... rolesAllowed) {
	ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
	for (SecurityRole role : rolesAllowed)
	    if (ctx.isUserInRole(role.getRoleName()))
		return;
	throw new UnauthorizedException("Уровень доступа недостаточен");
    }
}
