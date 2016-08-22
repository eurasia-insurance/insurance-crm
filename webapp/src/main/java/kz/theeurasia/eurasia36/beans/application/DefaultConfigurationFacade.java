package kz.theeurasia.eurasia36.beans.application;

import static kz.theeurasia.eurasia36.application.ParameterConstants.*;

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
    public SelectItem getNoneSI() {
	return new SelectItem(null, "любой");
    }
}
