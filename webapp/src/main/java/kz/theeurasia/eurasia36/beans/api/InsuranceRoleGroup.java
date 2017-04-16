package kz.theeurasia.eurasia36.beans.api;

import java.util.StringJoiner;

import com.lapsa.insurance.security.InsuranceRole;
import com.lapsa.utils.security.SecurityRoleGroup;

public enum InsuranceRoleGroup implements SecurityRoleGroup {

    VIEWERS_OWNED_ONLY(InsuranceRole.AGENT_ROLE),

    VIEWERS(
	    InsuranceRole.AGENT_ROLE,
	    InsuranceRole.SPECIALIST_ROLE,
	    InsuranceRole.ADMIN_ROLE),

    CHANGERS(
	    InsuranceRole.SPECIALIST_ROLE,
	    InsuranceRole.ADMIN_ROLE),

    CLOSERS(InsuranceRole.ADMIN_ROLE);

    private final InsuranceRole[] roles;

    private InsuranceRoleGroup(InsuranceRole... roles) {
	this.roles = roles;
    }

    @Override
    public InsuranceRole[] getRoles() {
	return roles;
    }

    @Override
    public String toString() {
	StringJoiner sj = new StringJoiner(", ", "[", "]");
	for (InsuranceRole r : roles)
	    sj.add(r.toString());
	return sj.toString();
    }
}
