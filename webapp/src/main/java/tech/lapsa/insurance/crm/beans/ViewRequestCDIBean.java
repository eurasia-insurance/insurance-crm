package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
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
    public static class TransactionCompleteCheckCDIBean
	    extends ASelectingChecker
	    implements Serializable {

	private static final long serialVersionUID = 1L;

	// allowed

	private boolean allowed = false;

	public boolean isAllowed() {
	    return allowed;
	}

	// signle

	private RequestRow<?> single = null;

	@PostConstruct
	public void init() {
	    final List<RequestRow<?>> list = getSelected();
	    allowed = isInRole(InsuranceRoleGroup.VIEWERS) //
		    && list.size() == 1 //
	    ;
	    if (!allowed)
		return;
	    single = list.get(0);
	}
    }

    // row

    private RequestRow<?> row;

    public RequestRow<?> getRow() {
	if (check.isAllowed())
	    return row;
	return null;
    }

    // CDIs

    // local

    @Inject
    private TransactionCompleteCheckCDIBean check;

    @PostConstruct
    public void init() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	row = check.isAllowed()
		? check.single
		: null;
    }

}
