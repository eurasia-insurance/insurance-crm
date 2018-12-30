package tech.lapsa.insurance.crm.rows;

import com.lapsa.insurance.domain.casco.Casco;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class PolicyRequestRowDataModel extends InsuranceRequestRowDataModel<PolicyRequest>
	implements RequestRow<PolicyRequest> {

    public PolicyRequestRowDataModel(PolicyRequest entity) {
	super(entity);
    }

    @Override
    public RequestType getType() {
	return RequestType.POLICY_REQUEST;
    }

    @Override
    public Policy getPolicy() {
	try {
	    return entity.getPolicy();
	} catch (NullPointerException e) {
	    return null;
	}
    }
}
