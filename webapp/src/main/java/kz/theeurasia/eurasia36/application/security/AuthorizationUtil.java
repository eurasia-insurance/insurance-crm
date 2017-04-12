package kz.theeurasia.eurasia36.application.security;

import java.util.HashSet;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import kz.theeurasia.eurasia36.beans.application.UnauthorizedException;

public class AuthorizationUtil {

    public static boolean isInRole(RoleGroup... roles) {
	ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
	for (RoleGroup roleGroup : roles)
	    for (SecurityRole r : roleGroup.getRoles())
		if (ctx.isUserInRole(r.getRoleName()))
		    return true;
	return false;
    }

    public static void checkRoleGranted(String message, RoleGroup... roles) {
	if (isInRole(roles))
	    return;
	throw new UnauthorizedException(message);
    }

    public static void checkRoleGranted(RoleGroup... roles) {
	SecurityRole[] list = allRolesList(roles);
	StringBuffer sb = new StringBuffer("Недостаточно прав доступа.");
	for (int i = 0; i < list.length; i++) {
	    if (i == 0)
		sb.append(" Требуется как минимум одна из следующих ролей доступа: ");
	    sb.append(list[i].toString());
	    if (i == list.length - 1)
		sb.append(".");
	    else
		sb.append(", ");
	}
	checkRoleGranted(sb.toString(), list);
    }

    public static void checkRoleDenied(String message, RoleGroup... roles) {
	if (!isInRole(roles))
	    return;
	throw new UnauthorizedException(message);
    }

    public static void checkRoleDenied(RoleGroup... roles) {
	SecurityRole[] list = allRolesList(roles);
	StringBuffer sb = new StringBuffer("Недостаточно прав доступа.");
	for (int i = 0; i < list.length; i++) {
	    if (i == 0)
		sb.append(" Доступ для ролей : ");
	    sb.append(list[i].toString());
	    if (i == list.length - 1)
		sb.append(" запрещен.");
	    else
		sb.append(", ");
	}
	checkRoleDenied(sb.toString(), list);
    }

    private static SecurityRole[] allRolesList(RoleGroup... roles) {
	Set<SecurityRole> rrr = new HashSet<>();
	for (RoleGroup rg : roles)
	    for (SecurityRole r : rg.getRoles())
		rrr.add(r);
	return rrr.toArray(new SecurityRole[] {});
    }
}
