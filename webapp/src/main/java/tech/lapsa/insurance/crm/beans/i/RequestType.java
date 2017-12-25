package tech.lapsa.insurance.crm.beans.i;

import tech.lapsa.java.commons.localization.LocalizedElement;

public enum RequestType implements LocalizedElement {

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
