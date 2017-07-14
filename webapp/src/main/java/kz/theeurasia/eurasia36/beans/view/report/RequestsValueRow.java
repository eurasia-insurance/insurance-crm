package kz.theeurasia.eurasia36.beans.view.report;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import com.lapsa.reports.table.ValueCell;
import com.lapsa.reports.table.ValueRow;
import com.lapsa.reports.table.impl.DefaultAmountValueCell;
import com.lapsa.reports.table.impl.DefaultDateTimeValueCell;
import com.lapsa.reports.table.impl.DefaultIntegerNumberValueCell;
import com.lapsa.reports.table.impl.DefaultTextValueCell;

import kz.theeurasia.eurasia36.beans.model.RequestRow;

public class RequestsValueRow implements ValueRow {

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

	    new FieldDescriptor("Дата принятия в работу", row -> new DefaultDateTimeValueCell(row.getAccepted())),
	    new FieldDescriptor("Кем принято в работу", row -> new DefaultTextValueCell(row.getAcceptedBy().getName())),

	    new FieldDescriptor("Дата завершения", row -> new DefaultDateTimeValueCell(row.getCompleted())),
	    new FieldDescriptor("Кем завершено", row -> new DefaultTextValueCell(row.getCompletedBy().getName())),

	    new FieldDescriptor("Дата закрытия", row -> new DefaultDateTimeValueCell(row.getClosed())),
	    new FieldDescriptor("Кем закрыто", row -> new DefaultTextValueCell(row.getClosedBy().getName())),

	    new FieldDescriptor("Сумма премии",
		    row -> new DefaultAmountValueCell(row.getAmount(), row.getCurrency())),

	    new FieldDescriptor("Имя заявителя", row -> new DefaultTextValueCell(row.getRequesterName())),
	    new FieldDescriptor("Email заявителя", row -> new DefaultTextValueCell(row.getRequesterEmail())),
	    new FieldDescriptor("Телефон заявителя", row -> new DefaultTextValueCell(row.getRequesterPhone())),

	    new FieldDescriptor("Статус заявки", row -> new DefaultTextValueCell(row.getRequestStatus())),

	    new FieldDescriptor("Стадия обработки", row -> new DefaultTextValueCell(row.getProgressStatus())),
	    new FieldDescriptor("Результат", row -> new DefaultTextValueCell(row.getTransactionStatus())),
	    new FieldDescriptor("Причина", row -> new DefaultTextValueCell(row.getTransactionProblem())),

	    new FieldDescriptor("Номер договора", row -> new DefaultTextValueCell(row.getAgreementNumber())),
	    new FieldDescriptor("Способ оплаты", row -> new DefaultTextValueCell(row.getPaymentMethod())),
	    new FieldDescriptor("Статус оплаты", row -> new DefaultTextValueCell(row.getPaymentStatus())),
	    new FieldDescriptor("Платежный референс", row -> new DefaultTextValueCell(row.getPaymentReference())),

	    new FieldDescriptor("Примечание", row -> new DefaultTextValueCell(row.getNote())),
    };

    private final List<ValueCell<?>> row;

    public RequestsValueRow(RequestRow<?> request) {
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
