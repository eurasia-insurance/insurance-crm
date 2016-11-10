package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

import com.lapsa.insurance.domain.InsuranceRequest;

public interface MainFacade {

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();

    String doResetRequest();

    String doSaveRequest();

    String doRefresh();

    String doCloseRequest();

    String doSetStart(InsuranceRequest insuranceRequest);

    String doSetOnProcess(InsuranceRequest insuranceRequest);

    String doSetOnHold(InsuranceRequest insuranceRequest);

    String doSetFinish(InsuranceRequest insuranceRequest);
}
