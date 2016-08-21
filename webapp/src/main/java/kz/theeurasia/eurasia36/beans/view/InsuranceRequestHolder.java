package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.persistence.dao.InsuranceRequestDAO;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;

import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;

@Named("insuranceRequest")
@ViewScoped
public class InsuranceRequestHolder implements Serializable {
    private static final long serialVersionUID = -2574434730269891652L;

    private InsuranceRequest value;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Inject
    private InsuranceRequestDAO insuranceRequestDAO;

    public InsuranceRequest getValue() {
	return value;
    }

    public void setValue(InsuranceRequest value) {
	this.value = value;
    }

    public String doSave() {
	try {
	    value = insuranceRequestDAO.save(value);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(e);
	}
	return null;
    }
}
