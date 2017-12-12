package tech.lapsa.insurance.crm.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import tech.lapsa.insurance.crm.beans.i.FunctionFacade;
import tech.lapsa.insurance.facade.EJBViaCDI;
import tech.lapsa.insurance.facade.EpaymentConnectionFacade;

@Named("functionFacade")
@ApplicationScoped
public class FunctionFacadeCDIBean implements FunctionFacade {

    @Inject
    @EJBViaCDI
    private EpaymentConnectionFacade payments;

    @Override
    public String paymentUrl(final String invoiceNumber) {
	try {
	    return payments.getPaymentURI(invoiceNumber).toASCIIString();
	} catch (Exception e) {
	    return null;
	}
    }

}
