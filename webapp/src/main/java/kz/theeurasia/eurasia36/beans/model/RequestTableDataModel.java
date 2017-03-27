package kz.theeurasia.eurasia36.beans.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.Request;
import com.lapsa.reports.ReportData;
import com.lapsa.reports.ReportGenerator;
import com.lapsa.reports.ReportGeneratorFactory;
import com.lapsa.reports.table.TableModel;

import kz.theeurasia.eurasia36.beans.view.report.RequestsTableModel;

public class RequestTableDataModel extends ListDataModel<RequestRow<?>>
	implements RequestList {

    public RequestTableDataModel(List<RequestRow<?>> rows) {
	setWrappedData(rows);
    }

    @Override
    public int getTotalCount() {
	return getRowCount();
    }

    @Override
    public Double getTotalAmount() {
	List<RequestRow<?>> list = getRows();
	double amount = 0d;
	for (RequestRow<?> entity : list)
	    amount += entity.getAmount();
	return amount;
    }

    @Override
    public Object getRowKey(RequestRow<?> object) {
	if (object == null || object.getId() == null)
	    return null;
	// check the value is in the list
	RequestRow<?> rr = getRowData(object.getId().toString());
	if (rr == null)
	    return null;
	return rr.getId().toString();
    }

    @Override
    public RequestRow<?> getRowData(String rowKey) {
	if (rowKey == null)
	    return null;
	Integer rowId = Integer.parseInt(rowKey);
	List<RequestRow<?>> list = getRows();
	for (RequestRow<?> entity : list) {
	    if (rowId.equals(entity.getId()))
		return entity;
	}
	return null;
    }

    @Override
    public List<Request> getEntitiesList() {
	List<Request> list = new ArrayList<>();
	List<RequestRow<?>> wrapped = getRows();
	for (RequestRow<?> row : wrapped)
	    list.add(row.getEntity());
	return list;
    }

    @Override
    public StreamedContent getAsExcel() {
	List<Request> list = getEntitiesList();
	TableModel model = new RequestsTableModel(list);
	ReportGenerator excelGenerator = ReportGeneratorFactory.createReportGenerator("excel");
	ReportData data = excelGenerator.generateTableReport(model);
	return new DefaultStreamedContent(data.contentAsInputStream(), data.contentType(), "report.xls");
    }

    @Override
    public List<RequestRow<?>> getRows() {
	@SuppressWarnings("unchecked")
	List<RequestRow<?>> rows = (List<RequestRow<?>>) getWrappedData();
	return rows;
    }

}
