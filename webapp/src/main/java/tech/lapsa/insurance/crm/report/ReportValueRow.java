package tech.lapsa.insurance.crm.report;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import com.lapsa.reports.table.ValueCell;
import com.lapsa.reports.table.ValueRow;
import com.lapsa.reports.table.impl.DefaultAmountValueCell;
import com.lapsa.reports.table.impl.DefaultBooleanValueCell;
import com.lapsa.reports.table.impl.DefaultDateTimeValueCell;
import com.lapsa.reports.table.impl.DefaultIntegerNumberValueCell;
import com.lapsa.reports.table.impl.DefaultTextValueCell;

import tech.lapsa.insurance.crm.rows.RequestRow;

public class ReportValueRow implements ValueRow {

    private static final DefaultTextValueCell EMPTY_CELL = new DefaultTextValueCell("");

    static class FieldDescriptor {
	private final String title;
	private final Function<RequestRow<?>, ValueCell<?>> cellSupplier;

	private FieldDescriptor(String title, Function<RequestRow<?>, ValueCell<?>> cellSupplier) {
	    this.title = title;
	    this.cellSupplier = cellSupplier;
	}

	String getTitle() {
	    return title;
	}
    }

    static final FieldDescriptor[] FIELDS = new FieldDescriptor[] {

	    new FieldDescriptor("Номер", row -> new DefaultIntegerNumberValueCell(row.getId())),
	    new FieldDescriptor("Тип", row -> new DefaultTextValueCell(row.getType())),
	    new FieldDescriptor("Вид", row -> new DefaultTextValueCell(row.getInsuranceRequestType())),

	    new FieldDescriptor("Дата создания", row -> new DefaultDateTimeValueCell(row.getCreated())),
	    new FieldDescriptor("Кем создана", row -> new DefaultTextValueCell(row.getCreatedBy().getName())),

	    new FieldDescriptor("Дата принятия в работу", row -> new DefaultDateTimeValueCell(row.getPicked())),
	    new FieldDescriptor("Кем принято в работу", row -> new DefaultTextValueCell(row.getPickedBy().getName())),

	    new FieldDescriptor("Дата завершения", row -> new DefaultDateTimeValueCell(row.getCompleted())),
	    new FieldDescriptor("Кем завершено", row -> new DefaultTextValueCell(row.getCompletedBy().getName())),

	    new FieldDescriptor("Сумма премии", row -> new DefaultAmountValueCell(row.getAmount(), row.getCurrency())),

	    new FieldDescriptor("Имя заявителя", row -> new DefaultTextValueCell(row.getRequesterName())),
	    new FieldDescriptor("Email заявителя", row -> new DefaultTextValueCell(row.getRequesterEmail())),
	    new FieldDescriptor("Телефон заявителя", row -> new DefaultTextValueCell(row.getRequesterPhone())),

	    new FieldDescriptor("Архивная", row -> new DefaultBooleanValueCell(row.isArchived())),

	    new FieldDescriptor("Стадия обработки", row -> new DefaultTextValueCell(row.getProgressStatus())),
	    new FieldDescriptor("Статус договора", row -> new DefaultTextValueCell(row.getInsuranceRequestStatus())),
	    new FieldDescriptor("Причина", row -> new DefaultTextValueCell(row.getInsuranceRequestCancellationReason())),

	    new FieldDescriptor("Номер договора", row -> new DefaultTextValueCell(row.getAgreementNumber())),
	    new FieldDescriptor("Способ оплаты", row -> new DefaultTextValueCell(row.getPaymentMethodName())),
	    new FieldDescriptor("Статус оплаты", row -> new DefaultTextValueCell(row.getPaymentStatus())),
	    new FieldDescriptor("Платежный референс", row -> new DefaultTextValueCell(row.getPaymentReference())),

	    new FieldDescriptor("Примечание", row -> new DefaultTextValueCell(row.getNote())),

	    new FieldDescriptor("utm_source", row -> new DefaultTextValueCell(row.getUTMSource())),
	    new FieldDescriptor("utm_medium", row -> new DefaultTextValueCell(row.getUTMMedium())),
	    new FieldDescriptor("utm_campaign", row -> new DefaultTextValueCell(row.getUTMCampaign())),
	    new FieldDescriptor("utm_content", row -> new DefaultTextValueCell(row.getUTMContent())),
	    new FieldDescriptor("utm_term", row -> new DefaultTextValueCell(row.getUTMTerm())),
    };

    private final List<ValueCell<?>> row;

    public ReportValueRow(RequestRow<?> request) {
	this.row = Arrays.stream(FIELDS).map(fd -> {
	    try {
		return fd.cellSupplier.apply(request);
	    } catch (RuntimeException e) {
		return EMPTY_CELL;
	    }
	}).collect(collectingAndThen(toList(), Collections::unmodifiableList));
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
