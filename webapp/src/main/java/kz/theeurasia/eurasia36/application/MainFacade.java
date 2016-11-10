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

    // parameter based

    String doAcceptRequest(InsuranceRequest insuranceRequest);

    String doResumeRequest(InsuranceRequest insuranceRequest);

    String doPauseRequest(InsuranceRequest insuranceRequest);

    String doCompleteRequest(InsuranceRequest insuranceRequest);

    String doResetRequest(InsuranceRequest insuranceRequest);
}
