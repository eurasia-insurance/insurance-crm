package kz.theeurasia.eurasia36.beans.view;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.lapsa.insurance.domain.InsuranceRequest;

public class InsuranceRequestDataModel extends ListDataModel<InsuranceRequest>
	implements SelectableDataModel<InsuranceRequest> {

    private List<InsuranceRequest> list;

    public InsuranceRequestDataModel(List<InsuranceRequest> list) {
	super(list);
	this.list = list;
    }

    @Override
    public Object getRowKey(InsuranceRequest object) {
	return object.getId().toString();
    }

    @Override
    public InsuranceRequest getRowData(String rowKey) {
	for (InsuranceRequest ir : list) {
	    if (ir.getId().toString().equals(rowKey))
		return ir;
	}
	return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setWrappedData(Object data) {
	super.setWrappedData(data);
	list = (data == null) ? null : (List<InsuranceRequest>) data;
    }
}