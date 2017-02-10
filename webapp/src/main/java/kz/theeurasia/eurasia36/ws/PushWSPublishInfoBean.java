package kz.theeurasia.eurasia36.ws;

import javax.inject.Singleton;

import com.lapsa.pushapi.ws.PushWSPublishInfo;

@Singleton
public class PushWSPublishInfoBean implements PushWSPublishInfo {
    public static final String WSPATH = "ws";

    @Override
    public String getWSPublishPath() {
	return WSPATH;
    }

}
