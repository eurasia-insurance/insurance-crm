package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

public interface MainFacade {

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();

    String doResetRequest();

    String doSaveRequest();
}
