package kz.theeurasia.eurasia36.beans.view.report;

import java.util.Iterator;
import java.util.List;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.reports.table.DefaultHeaderRow;
import com.lapsa.reports.table.HeaderRow;
import com.lapsa.reports.table.TableModel;
import com.lapsa.reports.table.ValueRow;

public class InsuranceRequestsTableModel implements TableModel {

    private final List<InsuranceRequest> list;
    private final HeaderRow headerRow;

    public InsuranceRequestsTableModel(List<InsuranceRequest> list) {
	this.list = list;
	this.headerRow = new DefaultHeaderRow(InsuranceRequestsValueRow.HEADER_ROW_CAPTIONS);
    }

    @Override
    public Iterator<ValueRow> iterator() {
	final Iterator<InsuranceRequest> i = list.iterator();
	return new Iterator<ValueRow>() {

	    @Override
	    public boolean hasNext() {
		return i.hasNext();
	    }

	    @Override
	    public ValueRow next() {
		return new InsuranceRequestsValueRow(i.next());
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
	return new InsuranceRequestsValueRow(list.get(number));
    }

}
