package kz.theeurasia.eurasia36.application;

import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

public interface MainFacade {

    void onFilterChanged();

    void onFilterChanged(AjaxBehaviorEvent event);

    String doInitialize();

    String doResetFilter();

    String doFilterCreatedToday();

    String doFilterCreatedYesterday();

    String doFilterCreatedThisWeek();

    String doFilterCreatedLastWeek();

    String doFilterCreatedThisMonth();

    String doFilterCreatedLastMonth();

    String doFilterCompletedToday();

    String doFilterCompletedYesterday();

    String doFilterCompletedThisWeek();

    String doFilterCompletedLastWeek();

    String doFilterCompletedThisMonth();

    String doFilterCompletedLastMonth();

    String doCancelEditRequest();

    String doRefresh();

    String doCloseRequest();

    void onDatatableDblSelect(SelectEvent event);

    String doAcceptRequestOnce();

    String doPauseRequest();

    String doResumeRequest();

    String doCompleteRequest();

    void onTransactionStatusChanged(AjaxBehaviorEvent event);

    void onObtainingMethodChanged(AjaxBehaviorEvent event);

    void onPaymentMethodChanged(AjaxBehaviorEvent event);

    void onActualPremiumCostChanged(AjaxBehaviorEvent event);

    void onDiscountAmountChanged(AjaxBehaviorEvent event);

    String doSetDiscount(double discountPercent);
}
