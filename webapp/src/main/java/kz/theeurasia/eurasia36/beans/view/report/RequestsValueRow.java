package kz.theeurasia.eurasia36.beans.view.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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

    public static final String[] HEADER_ROW_CAPTIONS = new String[] {
	    "Продукт", // 1
	    "Тип", // 2
	    "Номер", // 3

	    "Дата создания", // 4
	    "Кем создана", // 5
	    "Дата принятия в работу", // 6
	    "Дата завершения", // 7
	    "Дата закрытия", // 8

	    "Стоимость премии", // 9
	    "Имя заявителя", // 10
	    "Email заявителя", // 11
	    "Телефон заявителя", // 12
	    "Статус заявки", // 13
	    "Стадия обработки", // 14
	    "Результат", // 15
	    "Причина", // 16
	    "Номер договора", // 17
	    "Примечание" // 18
    };

    private final List<ValueCell<?>> row;

    public RequestsValueRow(final Request request) {
	List<ValueCell<?>> row = new ArrayList<>();

	InsuranceRequest insuranceRequest = null;
	if (request instanceof InsuranceRequest)
	    insuranceRequest = (InsuranceRequest) request;

	try {
	    row.add(new DefaultTextValueCell(insuranceRequest.getProductType())); // 1
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(insuranceRequest.getType())); // 2
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultIntegerNumberValueCell(request.getId())); // 3
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultDateTimeValueCell(request.getCreated())); // 4
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getCreatedBy() == null ? "" : request.getCreatedBy().getName())); // 5
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultDateTimeValueCell(request.getAccepted())); // 6
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultDateTimeValueCell(request.getCompleted())); // 7
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultDateTimeValueCell(request.getClosed())); // 8
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultAmountValueCell(insuranceRequest.getProduct().getCalculation().getPremiumCost(),
		    insuranceRequest.getProduct().getCalculation().getPremiumCurrency())); // 9
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getRequester().getName())); // 10
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getRequester().getEmail())); // 11
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getRequester().getPhone().getFormatted())); // 12
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getStatus())); // 13
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getProgressStatus())); // 14
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(insuranceRequest.getTransactionStatus())); // 15
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(insuranceRequest.getTransactionProblem())); // 16
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(insuranceRequest.getAgreementNumber())); // 17
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	try {
	    row.add(new DefaultTextValueCell(request.getNote())); // 18
	} catch (NullPointerException e) {
	    row.add(EMPTY_CELL);
	}

	this.row = Collections.unmodifiableList(row);
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
