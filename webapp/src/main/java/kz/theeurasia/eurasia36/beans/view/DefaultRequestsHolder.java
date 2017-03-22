package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.reports.ReportData;
import com.lapsa.reports.ReportGenerator;
import com.lapsa.reports.ReportGeneratorFactory;
import com.lapsa.reports.table.TableModel;

import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsHolder;
import kz.theeurasia.eurasia36.beans.view.report.InsuranceRequestsTableModel;

@Named("insuranceRequests")
@ViewScoped
public class DefaultInsuranceRequestsHolder extends DefaultWritableValueHolder<List<InsuranceRequest>>
	implements Serializable, InsuranceRequestsHolder {

    private static final long serialVersionUID = 7249376610273191727L;
    private InsuranceRequestDataModel model;

    @Override
    public void reset() {
	this.value = null;
	this.model = new InsuranceRequestDataModel(null);
    }

    @Override
    public List<InsuranceRequest> getRequests() {
	return super.getValue();
    }

    @Override
    public void setRequests(List<InsuranceRequest> requests) {
	super.setValue(requests);
	this.model = new InsuranceRequestDataModel(requests);
    }

    @Override
    public InsuranceRequestDataModel getModel() {
	return model;
    }

    @Override
    public int getRequestCountTotal() {
	return value.size();
    }

    @Override
    public double getRequestPremiumCostTotal() {
	double tot = 0d;
	for (InsuranceRequest ir : value)
	    tot += ir.getProduct().getCalculation().getPremiumCost();
	return tot;
    }

    @Override
    public StreamedContent getAsExcel() {
	TableModel model = new InsuranceRequestsTableModel(value);
	ReportGenerator excelGenerator = ReportGeneratorFactory.createReportGenerator("excel");
	ReportData data = excelGenerator.generateTableReport(model);
	return new DefaultStreamedContent(data.contentAsInputStream(), data.contentType(), "report.xls");
    }
}
