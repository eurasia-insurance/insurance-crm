package tech.lapsa.insurance.crm.beans.rows;

import java.util.ArrayList;
import java.util.List;

import com.lapsa.insurance.domain.CallbackRequest;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.policy.PolicyRequest;

public final class RequestsDataModelFactory {

    public static RequestList createList(List<Request> requests) {
	List<RequestRow<?>> rows = new ArrayList<>();
	for (Request r : requests) {
	    RequestRow<?> row = createRow(r);
	    rows.add(row);
	}
	return new RequestTableDataModel(rows);
    }

    public static RequestRow<?> createRow(Request request) {
	if (request instanceof PolicyRequest)
	    return new PolicyRequestRowDataModel((PolicyRequest) request);
	else if (request instanceof CascoRequest)
	    return new CascoRequestRowDataModel((CascoRequest) request);
	else if (request instanceof CallbackRequest)
	    return new CallbackRequestRowDataModel((CallbackRequest) request);
	else
	    throw new RuntimeException("Invalid type of request");
    }

}
