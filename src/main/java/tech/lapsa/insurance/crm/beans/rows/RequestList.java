package tech.lapsa.insurance.crm.beans.rows;

import java.util.List;

import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.StreamedContent;

import com.lapsa.insurance.domain.Request;

public interface RequestList extends SelectableDataModel<RequestRow<?>> {

    List<RequestRow<?>> getRows();

    int getTotalCount();

    Double getTotalAmount();

    StreamedContent getAsExcel();

    List<Request> getEntitiesList();

}