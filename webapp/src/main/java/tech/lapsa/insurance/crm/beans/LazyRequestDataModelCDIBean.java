package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.lapsa.insurance.domain.Request;

import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.ListWithStats;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.insurance.dao.RequestSort;
import tech.lapsa.insurance.dao.RequestSort.RequestSortField;
import tech.lapsa.insurance.dao.RequestSort.RequestSortOrder;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.patterns.dao.NotFound;

@Named("requests")
@ViewScoped
public class LazyRequestDataModelCDIBean extends LazyDataModel<RequestRow<?>> implements Serializable {

    private static final long serialVersionUID = 1L;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    // CDIs

    // local

    @Inject
    private RowsLoaderServiceCDIBean loader;

    @Override
    public RequestRow<?> getRowData(String rowKey) {
	try {
	    return RequestRow.from(requestDAO.getById(Integer.parseInt(rowKey)));
	} catch (NumberFormatException e) {
	    throw new FacesException(e);
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	} catch (NotFound e) {
	    throw new FacesException(e);
	}
    }

    @Override
    public Object getRowKey(RequestRow<?> rr) {
	return MyOptionals.of(rr).map(RequestRow::getId).orElse(null);
    }

    @Override
    public List<RequestRow<?>> load(int first, int pageSize, String sortFieldString, SortOrder sortOrderEnum,
	    Map<String, Object> filters) {

	final RequestSortField sortField = parseSortField(sortFieldString);
	final RequestSortOrder sortOrder = parseSortOrder(sortOrderEnum);
	final RequestSort sort = new RequestSort(sortField, sortOrder);

	final ListWithStats<? extends Request> list = loader.loadPart(first, pageSize, sort);

	int dataSize = list.getTotalCount() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) list.getTotalCount();
	this.setRowCount(dataSize);

	final List<RequestRow<?>> wrappedData = RequestRow.from(list.getPart());
	this.setWrappedData(wrappedData);
	return wrappedData;
    }

    private RequestSortOrder parseSortOrder(SortOrder sortOrderEnum) {
	switch (sortOrderEnum) {
	case ASCENDING:
	    return RequestSortOrder.ASC;
	case DESCENDING:
	    return RequestSortOrder.DESC;
	case UNSORTED:
	    break;
	}
	throw MyExceptions.format(FacesException::new, "No such order %1$s", sortOrderEnum);
    }

    private RequestSortField parseSortField(String textSortField) {
	switch (textSortField) {
	case "id":
	    return RequestSortField.ID;
	case "requesterName":
	    return RequestSortField.REQUESTER_NAME;
	case "updated":
	    return RequestSortField.UPDATED;
	case "created":
	    return RequestSortField.CREATED;
	case "requesterPhone":
	    return RequestSortField.REQUESTER_PHONE;
	case "requesterIdNumber":
	    return RequestSortField.REQUESTER_ID_NUMBER;
	}
	throw MyExceptions.format(FacesException::new, "No such  field %1$s", textSortField);
    }

}
