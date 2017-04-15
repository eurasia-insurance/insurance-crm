package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.security.InsuranceRoleGroup;

public interface CurrentUserHolder extends WritableValueHolder<User> {

    boolean inRoles(InsuranceRoleGroup... roles);

    boolean inRole(InsuranceRoleGroup role1);

    boolean inRole(InsuranceRoleGroup role1, InsuranceRoleGroup role2);

    boolean inRole(InsuranceRoleGroup role1, InsuranceRoleGroup role2, InsuranceRoleGroup role3);

}