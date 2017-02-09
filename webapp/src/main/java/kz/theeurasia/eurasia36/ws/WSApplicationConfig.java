package kz.theeurasia.eurasia36.ws;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.lapsa.pushapi.ws.PushGlobalWS;
import com.lapsa.pushapi.ws.PushSubscriptionWS;

@ApplicationPath("/ws")
public class WSApplicationConfig extends Application {

    private final Set<Class<?>> classes;

    public WSApplicationConfig() {
	HashSet<Class<?>> c = new HashSet<>();
	c.add(PushGlobalWS.class);
	c.add(PushSubscriptionWS.class);
	classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
	return classes;
    }
}
