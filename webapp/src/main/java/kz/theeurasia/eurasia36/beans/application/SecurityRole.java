package kz.theeurasia.eurasia36.beans.application;

public enum SecurityRole {
    REPORTER("reporter"),
    SPECIALIST("specialist"),
    SUPERVISOR("supervisor");

    private final String roleName;

    private SecurityRole(String roleName) {
	this.roleName = roleName;
    }

    public String getRoleName() {
	return roleName;
    }
}
