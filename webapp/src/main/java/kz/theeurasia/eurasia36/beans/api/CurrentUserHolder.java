package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.domain.crm.User;

import kz.theeurasia.eurasia36.beans.application.SecurityRole;

public interface CurrentUserHolder extends WritableValueHolder<User> {

    boolean inRoles(SecurityRole... roles);

    boolean inRole(SecurityRole role1, SecurityRole role2, SecurityRole role3);

    boolean inRole(SecurityRole role1, SecurityRole role2);

    boolean inRole(SecurityRole role1);
}