package kz.theeurasia.eurasia36.inject.producers;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggingProducer {
    @Produces
    private Logger createLogger(InjectionPoint injectionPoint) {
	return Logger.getLogger(injectionPoint.getMember().getDeclaringClass(). getName());
    }
}
