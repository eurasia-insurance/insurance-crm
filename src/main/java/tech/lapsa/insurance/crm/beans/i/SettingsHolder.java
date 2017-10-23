package tech.lapsa.insurance.crm.beans.i;

import tech.lapsa.insurance.crm.beans.RequestFilterImpl;

public interface SettingsHolder {

    void resetFilters();

    boolean isAdvanced();

    boolean isAutoRefresh();

    int getAutoRefreshInterval();

    RequestFilterImpl getRequestFilter();

    RequestType getRequestType();
}