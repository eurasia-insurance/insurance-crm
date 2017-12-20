package tech.lapsa.insurance.crm.beans;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.FacesException;
import javax.inject.Named;

import tech.lapsa.epayment.facade.EpaymentFacade.EpaymentFacadeRemote;
import tech.lapsa.epayment.facade.InvoiceNotFound;
import tech.lapsa.insurance.crm.beans.i.FunctionFacade;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Named("functionFacade")
@ApplicationScoped
public class FunctionFacadeCDIBean implements FunctionFacade {

    @EJB
    private EpaymentFacadeRemote epayments;

    @Override
    public String paymentUrl(final String invoiceNumber) {
	    try {
		return epayments.getDefaultPaymentURI(invoiceNumber).toASCIIString();
	} catch (IllegalArgument | InvoiceNotFound e) {
		throw new FacesException(e);
	    }
    }

}
