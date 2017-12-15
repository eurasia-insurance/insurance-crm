package tech.lapsa.insurance.crm.beans;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import tech.lapsa.insurance.crm.beans.i.FunctionFacade;
import tech.lapsa.insurance.facade.EpaymentConnectionFacade.EpaymentConnectionFacadeRemote;

@Named("functionFacade")
@ApplicationScoped
public class FunctionFacadeCDIBean implements FunctionFacade {

    @EJB
    private EpaymentConnectionFacadeRemote payments;

    @Override
    public String paymentUrl(final String invoiceNumber) {
	try {
	    return payments.getPaymentURI(invoiceNumber).toASCIIString();
	} catch (Exception e) {
	    return null;
	}
    }

}
