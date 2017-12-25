package tech.lapsa.insurance.crm.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import tech.lapsa.insurance.crm.beans.i.ConfigurationFacade;

@Named("configurationFacade")
@ApplicationScoped
public class ConfigurationFacadeBean implements ConfigurationFacade {

    @Override
    public SelectItem getAnySI() {
	return new SelectItem(null, "любой");
    }

    @Override
    public SelectItem getMustSelectSI() {
	return new SelectItem(null, "-- выберите значение --");
    }

    private static final String VERSION_RESOURCE_PATH = "/version.properties";
    private static final String VERSION_PARAMETER_NAME = "version";
    private static final String VERSION_DEFAULT = "UNKNOWN";

    private String version;

    @Override
    public String getVersion() {
	if (version == null)
	    version = retreiveVersion();
	return version;
    }

    // PRIVATE

    private String retreiveVersion() {
	try (InputStream stream = this.getClass().getResourceAsStream(VERSION_RESOURCE_PATH)) {
	    if (stream == null)
		return VERSION_DEFAULT;
	    Properties props = new Properties();
	    props.load(stream);
	    return (String) props.get(VERSION_PARAMETER_NAME);
	} catch (IOException e) {
	    return VERSION_DEFAULT;
	}
    }
}
