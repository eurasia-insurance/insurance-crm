package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
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

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkActionAllowed() {
	    return isInRole(InsuranceRoleGroup.VIEWERS) //
		    && getList().size() == 1 //
		    && getSignle().isCanView() 
	    ;
	}
    }

    // CDIs

    // local

    @Inject
    private ViewRequestCheckCDIBean checker;

    // row

    public RequestRow<?> getRow() {
	if (checker.isAllowed())
	    return checker.getSignle();
	return null;
    }

}
