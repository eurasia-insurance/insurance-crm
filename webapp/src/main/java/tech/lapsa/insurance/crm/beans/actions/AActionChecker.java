package tech.lapsa.insurance.crm.beans.actions;

import java.io.Serializable;
import java.util.function.Predicate;

import javax.inject.Inject;

import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.java.commons.function.MyObjects;

public abstract class AActionChecker implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Predicate<RequestsSelectionCDIBean> actionAllowedPredicate;

    protected AActionChecker(Predicate<RequestsSelectionCDIBean> actionAllowedPredicate) {
	this.actionAllowedPredicate = MyObjects.requireNonNull(actionAllowedPredicate, "actionAllowedPredicate");
    }

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean rrs;

    void setRrs(RequestsSelectionCDIBean rrs) {
	this.rrs = rrs;
    }

    public boolean isAllowed() {
	return actionAllowedPredicate.test(rrs);
    }
}
