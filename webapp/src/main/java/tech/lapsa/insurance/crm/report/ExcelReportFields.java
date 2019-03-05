package tech.lapsa.insurance.crm.report;

import com.lapsa.reports.table.impl.DefaultAmountValueCell;
import com.lapsa.reports.table.impl.DefaultBooleanValueCell;
import com.lapsa.reports.table.impl.DefaultDateTimeValueCell;
import com.lapsa.reports.table.impl.DefaultIntegerNumberValueCell;
import com.lapsa.reports.table.impl.DefaultTextValueCell;

public final class ExcelReportFields {

    private ExcelReportFields() {
    }

    public static final ReportFieldDescriptor[] FIELDS = new ReportFieldDescriptor[] {

	    new ReportFieldDescriptor("Номер", row -> new DefaultIntegerNumberValueCell(row.getId())),
	    new ReportFieldDescriptor("Тип", row -> new DefaultTextValueCell(row.getType())),
	    new ReportFieldDescriptor("Вид", row -> new DefaultTextValueCell(row.getInsuranceRequestType())),

	    new ReportFieldDescriptor("Дата создания", row -> new DefaultDateTimeValueCell(row.getCreated())),
	    new ReportFieldDescriptor("Кем создана", row -> new DefaultTextValueCell(row.getCreatedBy().getName())),

	    new ReportFieldDescriptor("Дата принятия в работу", row -> new DefaultDateTimeValueCell(row.getPicked())),
	    new ReportFieldDescriptor("Кем принято в работу", row -> new DefaultTextValueCell(row.getPickedBy().getName())),

	    new ReportFieldDescriptor("Дата завершения", row -> new DefaultDateTimeValueCell(row.getCompleted())),
	    new ReportFieldDescriptor("Кем завершено", row -> new DefaultTextValueCell(row.getCompletedBy().getName())),

	    new ReportFieldDescriptor("Расчитанная сумма премии",
		    row -> new DefaultAmountValueCell(row.getCalculatedAmount(), row.getCalculatedCurrency())),

	    new ReportFieldDescriptor("Имя заявителя", row -> new DefaultTextValueCell(row.getRequesterName())),
	    new ReportFieldDescriptor("Email заявителя", row -> new DefaultTextValueCell(row.getRequesterEmail())),
	    new ReportFieldDescriptor("Телефон заявителя", row -> new DefaultTextValueCell(row.getRequesterPhone())),

	    new ReportFieldDescriptor("Архивная", row -> new DefaultBooleanValueCell(row.isArchived())),

	    new ReportFieldDescriptor("Стадия обработки", row -> new DefaultTextValueCell(row.getProgressStatus())),
	    new ReportFieldDescriptor("Статус заявки на страхование",
		    row -> new DefaultTextValueCell(row.getInsuranceRequestStatus())),
	    new ReportFieldDescriptor("Причина отмены заявки на страхование",
		    row -> new DefaultTextValueCell(row.getInsuranceRequestCancellationReason())),

	    new ReportFieldDescriptor("Номер договора", row -> new DefaultTextValueCell(row.getAgreementNumber())),
	    new ReportFieldDescriptor("Фактически оплаченная сумма",
		    row -> new DefaultAmountValueCell(row.getPaymentAmount(), row.getPaymentCurrency())),
	    new ReportFieldDescriptor("Время фактической оплаты",
		    row -> new DefaultDateTimeValueCell(row.getPaymentInstant())),
	    new ReportFieldDescriptor("Способ оплаты", row -> new DefaultTextValueCell(row.getPaymentMethodName())),
	    new ReportFieldDescriptor("Референс оплаты", row -> new DefaultTextValueCell(row.getPaymentReference())),
	    new ReportFieldDescriptor("Имя плательщика", row -> new DefaultTextValueCell(row.getPaymentPayerName())),
	    new ReportFieldDescriptor("Номер карты плательщитка", row -> new DefaultTextValueCell(row.getPaymentCard())),

	    new ReportFieldDescriptor("Дата и время отмены оплаты",
		    row -> new DefaultDateTimeValueCell(row.getPaymentCanceledInstant())),

	    new ReportFieldDescriptor("Примечание", row -> new DefaultTextValueCell(row.getNote())),
    };
}
