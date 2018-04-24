package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyObjects;

public abstract class ASelectingChecker implements Serializable {

    private static final long serialVersionUID = 1L;

    // CDIs

    // local

    @Inject
    private RequestHolder requestHolder;

    public List<RequestRow<?>> getSelected() {
	return MyCollections.unmodifiableOrEmptyList(requestHolder.getValue());
    }

    public void setSelected(List<RequestRow<?>> selected) {
	requestHolder.setValue(MyCollections.unmodifiableOrEmptyList(MyObjects.requireNonNull(selected)));
    }

    public void setSelected(RequestRow<?> selected) {
	requestHolder
		.setValue(MyCollections.unmodifiableOrEmptyList(Arrays.asList(MyObjects.requireNonNull(selected))));
    }

    public void clearSelected() {
	requestHolder.setValue(null);
    }

}
