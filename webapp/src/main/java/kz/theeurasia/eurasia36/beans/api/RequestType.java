package kz.theeurasia.eurasia36.beans.api;

public enum RequestType {
    REQUEST("request"),
    INSURANCE_REQUEST("insurance"),
    POLICY_REQUEST("policy"),
    CASCO_REQUEST("casco"),
    CALLBACK_REQUEST("callback");

    final private String verb;

    private RequestType(String verb) {
	this.verb = verb;
    }

    public String getVerb() {
	return verb;
    }
}
