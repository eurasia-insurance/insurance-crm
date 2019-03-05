package tech.lapsa.insurance.crm.report;

import java.util.function.Function;

import com.lapsa.reports.table.ValueCell;

import tech.lapsa.insurance.crm.rows.RequestRow;

public class ReportFieldDescriptor {
    private final String title;
    private final Function<RequestRow<?>, ValueCell<?>> cellSupplier;

    public ReportFieldDescriptor(String title, Function<RequestRow<?>, ValueCell<?>> cellSupplier) {
	this.title = title;
	this.cellSupplier = cellSupplier;
    }

    public String getTitle() {
	return title;
    }

    public Function<RequestRow<?>, ValueCell<?>> getCellSupplier() {
        return cellSupplier;
    }
}
