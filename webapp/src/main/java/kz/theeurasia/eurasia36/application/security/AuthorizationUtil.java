package kz.theeurasia.eurasia36.application.security;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import kz.theeurasia.eurasia36.beans.application.UnauthorizedException;

public class AuthorizationUtil {

    public static boolean isInRole(RoleGroup... rolesAllowed) {
	ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
	for (RoleGroup roleGroup : rolesAllowed)
	    for (SecurityRole r : roleGroup.getRoles())
		if (ctx.isUserInRole(r.getRoleName()))
		    return true;
	return false;
    }

    public static void checkRoleAllowed(String message, RoleGroup... rolesAllowed) {
	if (isInRole(rolesAllowed))
	    return;
	throw new UnauthorizedException(message);
    }

    public static void checkRoleAllowed(RoleGroup... rolesAllowed) {
	StringBuffer sb = new StringBuffer(
		"Недостаточно прав доступа.");
	for (int i = 0; i < rolesAllowed.length; i++) {
	    if (i == 0)
		sb.append(" Требуется как минимум одна из следующих ролей доступа: ");
	    sb.append(rolesAllowed[i].toString());
	    if (i == rolesAllowed.length - 1)
		sb.append(".");
	    else
		sb.append(", ");
	}
	checkRoleAllowed(sb.toString(), rolesAllowed);
    }

}
