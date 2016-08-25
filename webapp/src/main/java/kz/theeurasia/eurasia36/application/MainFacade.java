package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

public interface MainFacade {

    String doCloseRequest();

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();

    String doResetRequest();

    void onRequestStatusFilterChanged(AjaxBehaviorEvent event);

    void onClosingResultChanged(AjaxBehaviorEvent event);
}
