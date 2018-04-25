package tech.lapsa.insurance.crm.beans.actions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyStreams;

public abstract class AActionChecker implements Serializable {

    private static final long serialVersionUID = 1L;

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean requestHolder;

    // list

    private List<RequestRow<?>> list = Collections.emptyList();

    @PostConstruct
    public void initList() {
	list = MyStreams.orEmptyOf(requestHolder.getValue()) //
		.collect(MyCollectors.unmodifiableList());
    }

    public List<RequestRow<?>> getList() {
	return list;
    }

    public Stream<RequestRow<?>> getListStream() {
	return MyStreams.orEmptyOf(list);
    }

    public RequestRow<?> getSignle() {
	return MyStreams.orEmptyOf(list).findFirst().orElse(null);
    }

    public Optional<RequestRow<?>> optSignle() {
	return MyStreams.orEmptyOf(list).findFirst();
    }

    public void refreshList() {
	requestHolder.refresh();
	initList();
    }

    public void updateList(List<RequestRow<?>> list) {
	requestHolder.setValue(list);
    }

    public void updateList(RequestRow<?> single) {
	requestHolder.setValue(Arrays.asList(single));
    }

    public void clearList() {
	this.list = Collections.emptyList();
    }

    public boolean isAllowed() {
	return checkActionAllowed();
    }

    protected abstract boolean checkActionAllowed();
}
