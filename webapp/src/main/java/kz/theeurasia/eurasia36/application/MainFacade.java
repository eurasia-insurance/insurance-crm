package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

public interface MainFacade {

    String doCloseRequest(InsuranceRequest order);

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    boolean vehicleHasImages(PolicyVehicle vehicle);

    boolean driverHasImages(PolicyDriver driver);

    String doResetFilter();
}
