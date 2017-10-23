package tech.lapsa.insurance.crm.ws;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.lapsa.pushapi.app.web.PushWS;

@ApplicationPath("/" + PushWSPublishInfoBean.WSPATH)
public class WSApplicationConfig extends Application {

    private final Set<Class<?>> classes;

    public WSApplicationConfig() {
	HashSet<Class<?>> c = new HashSet<>();
	c.add(PushWS.class);
	classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
	return classes;
    }
}
