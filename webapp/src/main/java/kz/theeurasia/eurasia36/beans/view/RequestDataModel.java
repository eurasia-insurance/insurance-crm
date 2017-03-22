package kz.theeurasia.eurasia36.beans.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.lapsa.insurance.domain.Request;

public class RequestDataModel extends ListDataModel<Request>
	implements SelectableDataModel<Request> {

    private List<Request> list;

    public RequestDataModel(List<Request> list) {
	super(list);
	this.list = list;
    }

    @Override
    public Object getRowKey(Request object) {
	return object.getId().toString();
    }

    @Override
    public Request getRowData(String rowKey) {
	for (Request ir : list) {
	    if (ir.getId().toString().equals(rowKey))
		return ir;
	}
	return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setWrappedData(Object data) {
	super.setWrappedData(data);
	list = (data == null) ? null : (List<Request>) data;
    }
}