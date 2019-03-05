package tech.lapsa.insurance.crm.report;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.lapsa.reports.table.ValueCell;
import com.lapsa.reports.table.ValueRow;
import com.lapsa.reports.table.impl.DefaultTextValueCell;

import tech.lapsa.insurance.crm.rows.RequestRow;

public class ReportValueRow implements ValueRow {

    private static final DefaultTextValueCell EMPTY_CELL = new DefaultTextValueCell("");


    private final List<ValueCell<?>> row;

    public ReportValueRow(RequestRow<?> request, final ReportFieldDescriptor[] fields) {
	this.row = Arrays.stream(fields)
		.map(fd -> {
		    try {
			return fd.getCellSupplier().apply(request);
		    } catch (RuntimeException e) {
			return EMPTY_CELL;
		    }
		})
		.collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    @Override
    public int getCellCount() {
	return row.size();
    }

    @Override
    public ValueCell<?> getCell(int number) {
	return row.get(number);
    }

    @Override
    public Iterator<ValueCell<?>> iterator() {
	return row.iterator();
    }

}
