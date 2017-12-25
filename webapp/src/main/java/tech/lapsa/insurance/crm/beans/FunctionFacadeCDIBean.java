package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Named;

import tech.lapsa.epayment.facade.EpaymentFacade.EpaymentFacadeRemote;
import tech.lapsa.epayment.facade.InvoiceNotFound;
import tech.lapsa.insurance.crm.beans.i.FunctionFacade;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Named("functionFacade")
@RequestScoped
public class FunctionFacadeCDIBean implements FunctionFacade, Serializable {

    private static final long serialVersionUID = 1L;

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
