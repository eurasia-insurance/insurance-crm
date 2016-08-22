package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.InsuranceRequest;

import kz.theeurasia.eurasia36.beans.api.WritableValueHolder;

@Named("insuranceRequest")
@ViewScoped
public class InsuranceRequestHolder extends DefaultWritableValueHolder<InsuranceRequest>
	implements Serializable, WritableValueHolder<InsuranceRequest> {
    private static final long serialVersionUID = -2574434730269891652L;
}
