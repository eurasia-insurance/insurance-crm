package kz.theeurasia.eurasia36.beans.application;

public enum SecurityRole {
    USER("user"),
    SUPER_USER("super-user");

    private final String roleName;

    private SecurityRole(String roleName) {
	this.roleName = roleName;
    }

    public String getRoleName() {
	return roleName;
    }
}
