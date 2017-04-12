package kz.theeurasia.eurasia36.application.security;

public enum SecurityRole implements RoleGroup {
    REPORTER("reporter"),
    SPECIALIST("specialist"),
    SUPERVISOR("supervisor"),
    PARTNER("partner");

    private final String roleName;

    private SecurityRole(String roleName) {
	this.roleName = roleName;
    }

    public String getRoleName() {
	return roleName;
    }

    @Override
    public SecurityRole[] getRoles() {
	return new SecurityRole[] { this };
    }
}
