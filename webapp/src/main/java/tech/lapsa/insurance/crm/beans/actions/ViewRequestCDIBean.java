package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;

@Named("viewRequest")
@RequestScoped
public class ViewRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("viewRequestCheck")
    @Dependent
    public static class ViewRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public ViewRequestCheckCDIBean() {
	    super(ViewRequestCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;
    }

    static boolean checkActionAllowed(RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.VIEWERS) //
		&& rrs != null
		&& rrs.isSingleValue()
		&& rrs.getSingleValue().isCanView();
    }

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean rrs;

    // row

    public RequestRow<?> getRow() {
	if (!checkActionAllowed(rrs))
	    return rrs.getSingleValue();
	return null;
    }
}
