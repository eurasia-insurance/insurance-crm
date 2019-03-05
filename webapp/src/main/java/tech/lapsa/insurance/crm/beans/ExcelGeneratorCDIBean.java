package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.Request;
import com.lapsa.reports.ReportData;
import com.lapsa.reports.ReportGenerator;
import com.lapsa.reports.ReportGeneratorFactory;
import com.lapsa.reports.table.TableModel;

import tech.lapsa.insurance.crm.report.ExcelReportFields;
import tech.lapsa.insurance.crm.report.ReportTableModel;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.ListWithStats;
import tech.lapsa.insurance.dao.RequestSort;

@Named("excel")
@RequestScoped
public class ExcelGeneratorCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private RowsLoaderServiceCDIBean rowsLoader;

    public StreamedContent getAsExcel() {
	final ListWithStats<? extends Request> list = rowsLoader.loadAll(RequestSort.DEFAULT_SORT);
	final List<RequestRow<?>> rows = RequestRow.from(list.getPart());
	final TableModel model = new ReportTableModel(ExcelReportFields.FIELDS, rows);
	final ReportGenerator excelGenerator = ReportGeneratorFactory.createReportGenerator("excel");
	final ReportData data = excelGenerator.generateTableReport(model);
	return new DefaultStreamedContent(data.contentAsInputStream(), data.contentType(), "report.xls");
    }
}
