package kz.theeurasia.eurasia36.beans.application;

import static kz.theeurasia.eurasia36.application.ParameterConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import kz.theeurasia.eurasia36.beans.api.ConfigurationFacade;

@Named("configurationFacade")
@ApplicationScoped
public class DefaultConfigurationFacade implements ConfigurationFacade {

    @Override
    public long getMaximumImagefileSize() {
	return MAXIMUM_IMAGE_UPLOAD_FILE_SIZE;
    }

    @Override
    public int getImageThumbnailWidth() {
	return IMAGE_THUMBNAIL_FIXED_WIDTH;
    }

    @Override
    public int getImageThumbnailHeight() {
	return IMAGE_THUMBNAIL_FIXED_HEIGHT;
    }

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
