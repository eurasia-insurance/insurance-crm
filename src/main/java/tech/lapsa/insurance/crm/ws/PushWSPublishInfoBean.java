package tech.lapsa.insurance.crm.ws;

import javax.inject.Singleton;

import com.lapsa.pushapi.app.api.PushWSPublishInfo;

@Singleton
public class PushWSPublishInfoBean implements PushWSPublishInfo {
    public static final String WSPATH = "ws";

    @Override
    public String getWSPublishPath() {
	return WSPATH;
    }

}
