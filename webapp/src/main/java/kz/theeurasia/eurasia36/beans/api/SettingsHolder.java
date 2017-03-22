package kz.theeurasia.eurasia36.beans.api;

import kz.theeurasia.eurasia36.beans.view.pojo.InsuranceRequestFilterBean;
import kz.theeurasia.eurasia36.beans.view.pojo.RequestFilterBean;

public interface SettingsHolder {

    void resetFilters();

    boolean isAdvanced();

    boolean isAutoRefresh();

    int getAutoRefreshInterval();

    RequestFilterBean getRequestFilter();

    InsuranceRequestFilterBean getInsuranceRequestFilter();

    RequestType getRequestType();
}