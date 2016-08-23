package kz.theeurasia.eurasia36.beans.api;

import javax.faces.model.SelectItem;

public interface ConfigurationFacade {

    long getMaximumImagefileSize();

    int getImageThumbnailWidth();

    int getImageThumbnailHeight();
    
    SelectItem getAnySI();
    
    SelectItem getMustSelectSI();
}
