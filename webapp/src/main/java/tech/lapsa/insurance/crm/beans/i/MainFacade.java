package tech.lapsa.insurance.crm.beans.i;

import org.primefaces.event.SelectEvent;

public interface MainFacade {

    String doInitialize();

    String doResetFilter();

    String doCancelEditRequest();

    void onDatatableDblSelect(SelectEvent event);

    String doAcceptRequestOnce();

    String doPauseRequest();

    String doResumeRequest();
}
