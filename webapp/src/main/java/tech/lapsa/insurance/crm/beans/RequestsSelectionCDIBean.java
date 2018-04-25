package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.Request;

import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyCollectors;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyStreams;
import tech.lapsa.patterns.dao.NotFound;

@Named("rqst")
@ViewScoped
public class RequestsSelectionCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    protected List<RequestRow<?>> value;

    public List<RequestRow<?>> getValue() {
	return value;
    }

    public void init() {
	reset();
    }

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    private Request quickRefresh(Request r) {
	try {
	    return requestDAO.restore(r);
	} catch (IllegalArgument e) {
	    return null;
	} catch (NotFound e) {
	    return null;
	}
    }

    public void setValue(List<RequestRow<?>> value) {
	this.value = MyStreams.orEmptyOf(value) //
		.map(RequestRow::getEntity) //
		.map(this::quickRefresh) //
		.filter(MyObjects::nonNull)
		.map(RequestRow::from)
		.collect(MyCollectors.unmodifiableList());
    }

    public void reset() {
	this.value = null;
    }

    public void setSingleRow(RequestRow<?> row) {
	setValue(Arrays.asList(row));
    }
}