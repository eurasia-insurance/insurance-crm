package tech.lapsa.insurance.crm.beans.i;

import javax.faces.model.SelectItem;

public interface ConfigurationFacade {

    SelectItem getAnySI();
    
    SelectItem getMustSelectSI();

    String getVersion();
}
