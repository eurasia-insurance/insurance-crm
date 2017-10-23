package tech.lapsa.insurance.crm.beans.i;

import javax.faces.model.SelectItem;

public interface ConfigurationFacade {

    long getMaximumImagefileSize();

    int getImageThumbnailWidth();

    int getImageThumbnailHeight();
    
    SelectItem getAnySI();
    
    SelectItem getMustSelectSI();

    String getVersion();
}
