package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
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
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStreams;
import tech.lapsa.patterns.dao.NotFound;

@Named("rqst")
@ViewScoped
public class RequestsSelectionCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void init() {
	reset();
    }

    // value

    protected List<RequestRow<?>> value;

    public List<RequestRow<?>> getValue() {
	return value;
    }

    public void refresh() {
	this.value = quickRefresh(value);
    }

    public void setValue(List<RequestRow<?>> value) {
	this.value = value;
    }

    public void reset() {
	this.value = null;
    }

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    private Request silentRestore(Request r) {
	try {
	    return requestDAO.getById(r.getId());
	} catch (IllegalArgument e) {
	    return null;
	} catch (NotFound e) {
	    return null;
	}
    }

    private RequestRow<?> quickRefresh(RequestRow<?> r) {
	return MyOptionals.of(r)
		.map(RequestRow::getEntity)
		.map(this::silentRestore)
		.map(RequestRow::from)
		.orElse(null);
    }

    private List<RequestRow<?>> quickRefresh(List<RequestRow<?>> rr) {
	return MyStreams.orEmptyOf(rr)
		.map(this::quickRefresh)
		.filter(MyObjects::nonNull)
		.collect(MyCollectors.unmodifiableList());
    }
}