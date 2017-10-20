package kz.theeurasia.eurasia36.application;

import java.util.StringJoiner;

import com.lapsa.utils.security.SecurityRole;
import com.lapsa.utils.security.SecurityRoleGroup;

import tech.lapsa.insurance.crm.auth.InsuranceSecurity.Role;

public enum InsuranceRoleGroup implements SecurityRoleGroup {

    VIEWERS_OWNED_ONLY(Role.AGENT),

    VIEWERS_GROUP_BASED(Role.SPECIALIST),

    VIEWERS_ALL(Role.ADMIN),

    VIEWERS(Role.AGENT, Role.SPECIALIST, Role.ADMIN),

    CHANGERS(Role.SPECIALIST, Role.ADMIN),

    CLOSERS(Role.ADMIN);

    private final SecurityRole[] roles;

    private InsuranceRoleGroup(SecurityRole... roles) {
	this.roles = roles;
    }

    @Override
    public SecurityRole[] getRoles() {
	return roles;
    }

    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner(", ", "[", "]");
	for (SecurityRole r : roles)
	    sj.add(r.toString());
	return sj.toString();
    }
}
