package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.domain.crm.User;

import kz.theeurasia.eurasia36.application.security.RoleGroup;

public interface CurrentUserHolder extends WritableValueHolder<User> {

    boolean inRoles(RoleGroup... roles);

    boolean inRole(RoleGroup role1, RoleGroup role2, RoleGroup role3);

    boolean inRole(RoleGroup role1, RoleGroup role2);

    boolean inRole(RoleGroup role1);
}