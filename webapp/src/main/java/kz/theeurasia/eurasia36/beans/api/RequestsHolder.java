package kz.theeurasia.eurasia36.beans.api;

import java.util.List;

import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.Request;

import kz.theeurasia.eurasia36.beans.view.RequestDataModel;

public interface RequestsHolder extends WritableValueHolder<List<Request>> {

    List<Request> getRequests();

    void setRequests(List<Request> requests);

    RequestDataModel getModel();

    int getRequestCountTotal();

    double getRequestPremiumCostTotal();

    StreamedContent getAsExcel();
}