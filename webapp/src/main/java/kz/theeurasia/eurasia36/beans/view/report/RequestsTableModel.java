package kz.theeurasia.eurasia36.beans.view.report;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.lapsa.insurance.domain.Request;
import com.lapsa.reports.table.HeaderRow;
import com.lapsa.reports.table.TableModel;
import com.lapsa.reports.table.ValueRow;
import com.lapsa.reports.table.impl.DefaultHeaderRow;

import kz.theeurasia.eurasia36.beans.view.report.RequestsValueRow.FieldDescriptor;

public class RequestsTableModel implements TableModel {

    private final List<Request> list;
    private final HeaderRow headerRow;

    public RequestsTableModel(List<Request> list) {
	this.list = list;
	this.headerRow = new DefaultHeaderRow(Arrays.stream(RequestsValueRow.FIELDS) //
		.map(FieldDescriptor::getTitle) //
		.toArray(String[]::new));
    }

    @Override
    public Iterator<ValueRow> iterator() {
	final Iterator<Request> i = list.iterator();
	return new Iterator<ValueRow>() {

	    @Override
	    public boolean hasNext() {
		return i.hasNext();
	    }

	    @Override
	    public ValueRow next() {
		return new RequestsValueRow(i.next());
	    }
	};
    }

    @Override
    public HeaderRow getHeaderRow() {
	return headerRow;
    }

    @Override
    public int getRowCount() {
	return list.size();
    }

    @Override
    public ValueRow getRow(int number) {
	return new RequestsValueRow(list.get(number));
    }

}
