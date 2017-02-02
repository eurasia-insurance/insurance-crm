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
