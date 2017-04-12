package kz.theeurasia.eurasia36.application.security;

public enum SecurityRoleGroup implements RoleGroup {

    VIEW_OWN_OWNED(SecurityRole.PARTNER),

    VIEWERS(
	    SecurityRole.PARTNER,
	    SecurityRole.REPORTER,
	    SecurityRole.SPECIALIST,
	    SecurityRole.SUPERVISOR),

    CHANGERS(
	    SecurityRole.SPECIALIST,
	    SecurityRole.SUPERVISOR),

    CLOSERS(SecurityRole.SUPERVISOR);

    private final SecurityRole[] roles;

    private SecurityRoleGroup(SecurityRole... roles) {
	this.roles = roles;
    }

    @Override
    public SecurityRole[] getRoles() {
	return roles;
    }
}
