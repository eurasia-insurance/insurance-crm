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

	row.add(insuranceRequest == null ? new DefaultTextValueCell("")
		: new DefaultTextValueCell(insuranceRequest.getProductType())); // 1
	row.add(insuranceRequest == null ? new DefaultTextValueCell("")
		: new DefaultTextValueCell(insuranceRequest.getType())); // 2
	row.add(new DefaultIntegerNumberValueCell(request.getId())); // 3
	row.add(new DefaultDateTimeValueCell(request.getCreated())); // 4
	row.add(new DefaultTextValueCell(request.getCreatedBy() == null ? "" : request.getCreatedBy().getName())); // 5
	row.add(new DefaultDateTimeValueCell(request.getAccepted())); // 6
	row.add(new DefaultDateTimeValueCell(request.getCompleted())); // 7
	row.add(new DefaultDateTimeValueCell(request.getClosed())); // 8
	row.add(insuranceRequest == null ? new DefaultTextValueCell("")
		: new DefaultAmountValueCell(insuranceRequest.getProduct().getCalculation().getPremiumCost(),
			insuranceRequest.getProduct().getCalculation().getPremiumCurrency())); // 9
	row.add(new DefaultTextValueCell(request.getRequester().getName())); // 10
	row.add(new DefaultTextValueCell(request.getRequester().getEmail())); // 11
	row.add(new DefaultTextValueCell(request.getRequester().getPhone())); // 12
	row.add(new DefaultTextValueCell(request.getStatus())); // 13
	row.add(new DefaultTextValueCell(request.getProgressStatus())); // 14

	row.add(insuranceRequest == null ? new DefaultTextValueCell("")
		: new DefaultTextValueCell(insuranceRequest.getTransactionStatus())); // 15

	row.add(insuranceRequest == null ? new DefaultTextValueCell("")
		: new DefaultTextValueCell(insuranceRequest.getTransactionProblem())); // 16

	row.add(insuranceRequest == null ? new DefaultTextValueCell("")
		: new DefaultTextValueCell(insuranceRequest.getAgreementNumber())); // 17

	row.add(request == null ? new DefaultTextValueCell("")
		: new DefaultTextValueCell(request.getNote())); // 18

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
