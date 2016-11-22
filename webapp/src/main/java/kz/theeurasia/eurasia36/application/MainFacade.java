package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

public interface MainFacade {

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();

    String doCancelEditRequest();

    String doRefresh();

    String doCloseRequest();

    String doAcceptRequestOnce();

    String doPauseRequest();

    String doResumeRequest();

    String doCompleteRequest();

    void onTransactionStatusChanged(AjaxBehaviorEvent event);

    void onObtainingMethodChanged(AjaxBehaviorEvent event);

    void onPaymentMethodChanged(AjaxBehaviorEvent event);

}
