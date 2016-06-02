package kz.theeurasia.eurasia36.application;

public enum UIMessages {

    // global names
    NAME_COMPANY_SHORT("name.company-short"),
    NAME_COMPANY_FULL("name.company-full"),
    NAME_SITE("name.site"),
    
    // errors
    ERROR_INTERNAL_SERVER_ERROR("error.internal-server-error"),
    //
    ;

    private final String key;

    public static final String BUNDLE_BASENAME = "UIMessages";
    public static final String BUNDLE_VAR = "ui";

    UIMessages(String key) {
	this.key = key;
    }

    public String getKey() {
	return key;
    }

}
