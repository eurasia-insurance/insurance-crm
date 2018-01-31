package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import tech.lapsa.java.commons.function.MyStrings;

@Named("global")
@RequestScoped
public class GlobalSettingsCDIBean implements Serializable {

    private static final String JNDI_NAME_CONFIGURATION = "insurance/resource/crm/Configuration";

    private static final long serialVersionUID = 1L;

    @Resource(name = JNDI_NAME_CONFIGURATION, authenticationType = AuthenticationType.CONTAINER)
    private Properties settings;

    public String iconStyleClassByUTMSource(final String utmSource) {
	if (MyStrings.empty(utmSource))
	    return "";
	return settings.getProperty("icon-class.utm-source." + utmSource, "") //
		.replaceAll("____", " ");
    }
}
