package kz.theeurasia.eurasia36.beans.view.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.reports.table.ValueCell;
import com.lapsa.reports.table.ValueRow;
import com.lapsa.reports.table.impl.DefaultAmountValueCell;
import com.lapsa.reports.table.impl.DefaultDateTimeValueCell;
import com.lapsa.reports.table.impl.DefaultIntegerNumberValueCell;
import com.lapsa.reports.table.impl.DefaultTextValueCell;

public class InsuranceRequestsValueRow implements ValueRow {

    public static final String[] HEADER_ROW_CAPTIONS = new String[] {
	    "Продукт", // 1
	    "Тип", // 2
	    "Номер", // 3
	    "Дата создания", // 4
	    "Дата обновления", // 5
	    "Стоимость премии", // 6
	    "Имя заявителя", // 7
	    "Email заявителя", // 8
	    "Телефон заявителя", // 9
	    "Статус заявки", // 10
	    "Стадия обработки", // 11
	    "Дата завершения", // 12
	    "Результат", // 13
	    "Причина"// 14
    };

    private final List<ValueCell<?>> row;

    public InsuranceRequestsValueRow(final InsuranceRequest request) {
	List<ValueCell<?>> row = new ArrayList<>();
	row.add(new DefaultTextValueCell(request.getProductType()));// 1
	row.add(new DefaultTextValueCell(request.getType()));// 2
	row.add(new DefaultIntegerNumberValueCell(request.getId()));// 3
	row.add(new DefaultDateTimeValueCell(request.getCreated()));// 4
	row.add(new DefaultDateTimeValueCell(request.getUpdated()));// 5
	row.add(new DefaultAmountValueCell(request.getProduct().getCalculation().getPremiumCost(),
		request.getProduct().getCalculation().getPremiumCurrency()));// 6
	row.add(new DefaultTextValueCell(request.getRequester().getName()));// 7
	row.add(new DefaultTextValueCell(request.getRequester().getEmail()));// 8
	row.add(new DefaultTextValueCell(request.getRequester().getPhone()));// 9
	row.add(new DefaultTextValueCell(request.getStatus()));// 10
	row.add(new DefaultTextValueCell(request.getProgressStatus()));// 11
	row.add(new DefaultDateTimeValueCell(request.getCompleted()));// 12
	row.add(new DefaultTextValueCell(request.getTransactionStatus()));// 13
	row.add(new DefaultTextValueCell(request.getTransactionProblem()));// 14
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
