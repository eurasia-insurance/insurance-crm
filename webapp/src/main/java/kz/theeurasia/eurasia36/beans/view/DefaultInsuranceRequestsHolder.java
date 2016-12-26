package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.InsuranceRequest;

import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsHolder;

@Named("insuranceRequests")
@ViewScoped
public class DefaultInsuranceRequestsHolder extends DefaultWritableValueHolder<List<InsuranceRequest>>
	implements Serializable, InsuranceRequestsHolder {

    private static final long serialVersionUID = 7249376610273191727L;
    private InsuranceRequestDataModel model;
    private InsuranceRequestsExcel excel;

    @Override
    public void reset() {
	this.value = null;
	this.model = new InsuranceRequestDataModel(null);
	this.excel = null;
    }

    @Override
    public List<InsuranceRequest> getRequests() {
	return super.getValue();
    }

    @Override
    public void setRequests(List<InsuranceRequest> requests) {
	super.setValue(requests);
	this.model = new InsuranceRequestDataModel(requests);
	this.excel = new InsuranceRequestsExcel(requests);
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
	return new DefaultStreamedContent(excel.asExcelWorkbookInputStream(), "application/vnd.ms-excel", "report.xls");
    }
}
