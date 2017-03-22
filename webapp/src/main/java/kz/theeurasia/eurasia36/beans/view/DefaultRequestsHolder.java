package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.reports.ReportData;
import com.lapsa.reports.ReportGenerator;
import com.lapsa.reports.ReportGeneratorFactory;
import com.lapsa.reports.table.TableModel;

import kz.theeurasia.eurasia36.beans.api.RequestsHolder;
import kz.theeurasia.eurasia36.beans.view.report.RequestsTableModel;

@Named("insuranceRequests")
@ViewScoped
public class DefaultRequestsHolder extends DefaultWritableValueHolder<List<Request>>
	implements Serializable, RequestsHolder {

    private static final long serialVersionUID = 7249376610273191727L;
    private RequestDataModel model;

    @Override
    public void reset() {
	this.value = null;
	this.model = new RequestDataModel(null);
    }

    @Override
    public List<Request> getRequests() {
	return super.getValue();
    }

    @Override
    public void setRequests(List<Request> requests) {
	super.setValue(requests);
	this.model = new RequestDataModel(requests);
    }

    @Override
    public RequestDataModel getModel() {
	return model;
    }

    @Override
    public int getRequestCountTotal() {
	return value.size();
    }

    @Override
    public double getRequestPremiumCostTotal() {
	double tot = 0d;
	for (Request ir : value)
	    if (ir instanceof InsuranceRequest) {
		tot += ((InsuranceRequest) ir).getProduct().getCalculation().getPremiumCost();
	    }
	return tot;
    }

    @Override
    public StreamedContent getAsExcel() {
	TableModel model = new RequestsTableModel(value);
	ReportGenerator excelGenerator = ReportGeneratorFactory.createReportGenerator("excel");
	ReportData data = excelGenerator.generateTableReport(model);
	return new DefaultStreamedContent(data.contentAsInputStream(), data.contentType(), "report.xls");
    }
}
