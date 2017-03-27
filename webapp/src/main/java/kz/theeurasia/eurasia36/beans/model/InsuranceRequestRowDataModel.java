package kz.theeurasia.eurasia36.beans.model;

import com.lapsa.insurance.crm.InsuranceRequestType;
import com.lapsa.insurance.crm.TransactionStatus;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.elements.InsuranceProductType;

public abstract class InsuranceRequestRowDataModel<T extends InsuranceRequest> extends RequestRowDataModel<T>
	implements RequestRow<T> {

    public InsuranceRequestRowDataModel(T entity) {
	super(entity);
    }

    @Override
    public TransactionStatus getTransactionStatus() {
	return entity.getTransactionStatus();
    }

    @Override
    public InsuranceProductType insuranceProductType() {
	return entity.getProductType();
    }

    @Override
    public InsuranceRequestType insuranceRequestType() {
	return entity.getType();
    }

    @Override
    public Double getAmount() {
	return entity.getProduct().getCalculation().getPremiumCost();
    }
}
