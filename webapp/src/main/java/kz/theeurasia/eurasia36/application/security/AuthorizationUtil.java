package kz.theeurasia.eurasia36.application.security;

import java.util.HashSet;
import java.util.Set;

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
	SecurityRole[] allRolesList = allRolesList(rolesAllowed);
	StringBuffer sb = new StringBuffer("Недостаточно прав доступа.");
	for (int i = 0; i < allRolesList.length; i++) {
	    if (i == 0)
		sb.append(" Требуется как минимум одна из следующих ролей доступа: ");
	    sb.append(allRolesList[i].toString());
	    if (i == allRolesList.length - 1)
		sb.append(".");
	    else
		sb.append(", ");
	}
	checkRoleAllowed(sb.toString(), allRolesList);
    }

    public static void checkRoleDenied(String message, RoleGroup... rolesDenied) {
	if (!isInRole(rolesDenied))
	    return;
	throw new UnauthorizedException(message);
    }

    public static void checkRoleDenied(RoleGroup... rolesDenied) {
	SecurityRole[] allRolesList = allRolesList(rolesDenied);
	StringBuffer sb = new StringBuffer("Недостаточно прав доступа.");
	for (int i = 0; i < allRolesList.length; i++) {
	    if (i == 0)
		sb.append(" Доступ для ролей : ");
	    sb.append(allRolesList[i].toString());
	    if (i == allRolesList.length - 1)
		sb.append(" запрещен.");
	    else
		sb.append(", ");
	}
	checkRoleDenied(sb.toString(), allRolesList);
    }

    private static SecurityRole[] allRolesList(RoleGroup... roles) {
	Set<SecurityRole> rrr = new HashSet<>();
	for (RoleGroup rg : roles)
	    for (SecurityRole r : rg.getRoles())
		rrr.add(r);
	return rrr.toArray(new SecurityRole[] {});
    }
}
