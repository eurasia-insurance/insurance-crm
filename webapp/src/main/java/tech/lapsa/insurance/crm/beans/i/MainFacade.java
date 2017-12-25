package tech.lapsa.insurance.crm.beans.i;

import org.primefaces.event.SelectEvent;

public interface MainFacade {

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

    void onDatatableDblSelect(SelectEvent event);

    String doAcceptRequestOnce();

    String doPauseRequest();

    String doResumeRequest();
}
