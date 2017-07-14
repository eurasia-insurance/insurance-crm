package kz.theeurasia.eurasia36.beans.view.report;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.reports.table.ValueCell;
import com.lapsa.reports.table.ValueRow;
import com.lapsa.reports.table.impl.DefaultAmountValueCell;
import com.lapsa.reports.table.impl.DefaultDateTimeValueCell;
import com.lapsa.reports.table.impl.DefaultIntegerNumberValueCell;
import com.lapsa.reports.table.impl.DefaultTextValueCell;

public class RequestsValueRow implements ValueRow {

    private static final DefaultTextValueCell EMPTY_CELL = new DefaultTextValueCell("");

    static class FieldDescriptor {
	private final String title;
	private final BiFunction<Request, InsuranceRequest, ValueCell<?>> cellSupplier;

	private FieldDescriptor(String title, BiFunction<Request, InsuranceRequest, ValueCell<?>> cellSupplier) {
	    this.title = title;
	    this.cellSupplier = cellSupplier;
	}

	String getTitle() {
	    return title;
	}
    }

    static final FieldDescriptor[] FIELDS = new FieldDescriptor[] {
	    new FieldDescriptor("Продукт",
		    (request, insuranceRequest) -> new DefaultTextValueCell(insuranceRequest.getProductType())),
	    new FieldDescriptor("Тип",
		    (request, insuranceRequest) -> new DefaultTextValueCell(insuranceRequest.getType())),
	    new FieldDescriptor("Номер",
		    (request, insuranceRequest) -> new DefaultIntegerNumberValueCell(request.getId())),
	    new FieldDescriptor("Дата создания",
		    (request, insuranceRequest) -> new DefaultDateTimeValueCell(request.getCreated())),
	    new FieldDescriptor("Кем создана",
		    (request, insuranceRequest) -> new DefaultTextValueCell(
			    request.getCreatedBy() == null ? "" : request.getCreatedBy().getName())),
	    new FieldDescriptor("Дата принятия в работу",
		    (request, insuranceRequest) -> new DefaultDateTimeValueCell(request.getAccepted())),
	    new FieldDescriptor("Дата завершения",
		    (request, insuranceRequest) -> new DefaultDateTimeValueCell(request.getCompleted())),
	    new FieldDescriptor("Дата закрытия",
		    (request, insuranceRequest) -> new DefaultDateTimeValueCell(request.getClosed())),
	    new FieldDescriptor("Размер премии",
		    (request, insuranceRequest) -> new DefaultAmountValueCell(
			    insuranceRequest.getProduct().getCalculation().getPremiumCost(),
			    insuranceRequest.getProduct().getCalculation().getPremiumCurrency())),
	    new FieldDescriptor("Имя заявителя",
		    (request, insuranceRequest) -> new DefaultTextValueCell(request.getRequester().getName())),
	    new FieldDescriptor("Email заявителя",
		    (request, insuranceRequest) -> new DefaultTextValueCell(request.getRequester().getEmail())),
	    new FieldDescriptor("Телефон заявителя",
		    (request, insuranceRequest) -> new DefaultTextValueCell(
			    request.getRequester().getPhone().getFormatted())),
	    new FieldDescriptor("Статус заявки",
		    (request, insuranceRequest) -> new DefaultTextValueCell(request.getStatus())),
	    new FieldDescriptor("Стадия обработки",
		    (request, insuranceRequest) -> new DefaultTextValueCell(request.getProgressStatus())),
	    new FieldDescriptor("Результат",
		    (request, insuranceRequest) -> new DefaultTextValueCell(insuranceRequest.getTransactionStatus())),
	    new FieldDescriptor("Причина",
		    (request, insuranceRequest) -> new DefaultTextValueCell(insuranceRequest.getTransactionProblem())),
	    new FieldDescriptor("Номер договора",
		    (request, insuranceRequest) -> new DefaultTextValueCell(insuranceRequest.getAgreementNumber())),
	    new FieldDescriptor("Примечание",
		    (request, insuranceRequest) -> new DefaultTextValueCell(request.getNote())),
    };

    private final List<ValueCell<?>> row;

    public RequestsValueRow(final Request request) {
	final InsuranceRequest insuranceRequest;
	if (request instanceof InsuranceRequest)
	    insuranceRequest = (InsuranceRequest) request;
	else
	    insuranceRequest = null;

	this.row = Arrays.stream(FIELDS).map(fd -> {
	    try {
		return fd.cellSupplier.apply(request, insuranceRequest);
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
