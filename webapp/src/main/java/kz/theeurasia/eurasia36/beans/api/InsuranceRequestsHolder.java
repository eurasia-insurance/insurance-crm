package kz.theeurasia.eurasia36.beans.api;

import java.util.List;

import com.lapsa.insurance.domain.InsuranceRequest;

public interface InsuranceRequestsHolder extends WritableValueHolder<List<InsuranceRequest>> {

    List<InsuranceRequest> getRequests();

    void setRequests(List<InsuranceRequest> requests);

}