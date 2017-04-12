package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import kz.theeurasia.eurasia36.application.security.SecurityRoleGroup;
import kz.theeurasia.eurasia36.beans.api.CurrentUserHolder;
import kz.theeurasia.eurasia36.beans.api.RequestType;
import kz.theeurasia.eurasia36.beans.api.SettingsHolder;
import kz.theeurasia.eurasia36.beans.view.pojo.RequestFilterBean;

@Named("settings")
@ViewScoped
public class DefaultSettingsHolder implements Serializable, SettingsHolder {
    private static final long serialVersionUID = -6980458753963030228L;

    private boolean advanced = false;
    private boolean autoRefresh = true;
    private int autoRefreshInterval = 30;

    private RequestFilterBean requestFilter;

    private RequestType requestType;

    @Inject
    private CurrentUserHolder currentUser;

    @Override
    @PostConstruct
    public void resetFilters() {
	if (currentUser.inRole(SecurityRoleGroup.VIEW_OWN_OWNED))
	    requestFilter = new RequestFilterBean(currentUser.getValue());
	else
	    requestFilter = new RequestFilterBean();
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
    public RequestFilterBean getRequestFilter() {
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
