package kz.theeurasia.eurasia36.beans.model;

import java.util.List;

import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.Request;

public interface RequestList {

    List<RequestRow<?>> getRows();

    int getTotalCount();

    Double getTotalAmount();

    StreamedContent getAsExcel();

    List<Request> getEntitiesList();

}