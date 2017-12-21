package tech.lapsa.insurance.crm.beans.i;

import tech.lapsa.insurance.dao.RequestFilter;

public interface SettingsHolder {

    void resetFilters();

    boolean isAdvanced();

    boolean isAutoRefresh();

    int getAutoRefreshInterval();

    RequestFilter getRequestFilter();

    RequestType getRequestType();
}