package kz.theeurasia.eurasia36.beans.api;

import java.util.List;

import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.InsuranceRequest;

import kz.theeurasia.eurasia36.beans.view.InsuranceRequestDataModel;

public interface InsuranceRequestsHolder extends WritableValueHolder<List<InsuranceRequest>> {

    List<InsuranceRequest> getRequests();

    void setRequests(List<InsuranceRequest> requests);

    InsuranceRequestDataModel getModel();

    int getRequestCountTotal();

    double getRequestPremiumCostTotal();

    StreamedContent getAsExcel();
}