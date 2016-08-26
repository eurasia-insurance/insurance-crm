package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

public interface MainFacade {

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();

    void onRequestStatusFilterChanged(AjaxBehaviorEvent event);

    String doResetRequest();

    String doSaveRequest();
}
