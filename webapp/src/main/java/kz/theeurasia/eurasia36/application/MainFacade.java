package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

import com.lapsa.insurance.domain.InsuranceRequest;

public interface MainFacade {

    String doCloseRequest(InsuranceRequest order);

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();
}
