package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.insurance.crm.beans.i.SettingsHolder;

@Named("settings")
@ViewScoped
public class SettingsHolderBean implements Serializable, SettingsHolder {
    private static final long serialVersionUID = -6980458753963030228L;

    private boolean advanced = false;
    private boolean autoRefresh = true;
    private int autoRefreshInterval = 30;

    private RequestFilterImpl requestFilter;

    private RequestType requestType;

    @Override
    @PostConstruct
    public void resetFilters() {
	requestFilter = new RequestFilterImpl();
	requestType = RequestType.REQUEST;
    }

    // GENERATED

    @Override
    public boolean isAdvanced() {
	return advanced;
    }

    public void setAdvanced(boolean advanced) {
	this.advanced = advanced;
    }

    @Override
    public boolean isAutoRefresh() {
	return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
	this.autoRefresh = autoRefresh;
    }

    @Override
    public int getAutoRefreshInterval() {
	return autoRefreshInterval;
    }

    public void setAutoRefreshInterval(int autoRefreshInterval) {
	this.autoRefreshInterval = autoRefreshInterval;
    }

    @Override
    public RequestFilterImpl getRequestFilter() {
	return requestFilter;
    }

    @Override
    public RequestType getRequestType() {
	return requestType;
    }

    public void setRequestType(RequestType requestType) {
	this.requestType = requestType;
    }

}
