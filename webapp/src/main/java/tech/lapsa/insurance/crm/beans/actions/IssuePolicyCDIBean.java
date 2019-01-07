package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Currency;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.InsurantData;
import com.lapsa.insurance.domain.PersonalData;
import com.lapsa.insurance.domain.policy.Policy;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.crm.validation.ValidPolicyAgreementByNumber;
import tech.lapsa.insurance.crm.validation.ValidPolicyNumber;
import tech.lapsa.insurance.facade.InsuranceRequestFacade.InsuranceRequestFacadeRemote;
import tech.lapsa.insurance.facade.PolicyFacade.PolicyFacadeRemote;
import tech.lapsa.insurance.facade.PolicyNotFound;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

@Named("issuePolicy")
@RequestScoped
public class IssuePolicyCDIBean implements ActionCDIBean, Serializable {

    private static final long serialVersionUID = 1L;

    @Named("issuePolicyCheck")
    @Dependent
    public static class IssuePolicyCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public IssuePolicyCheckCDIBean() {
	    super(IssuePolicyCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;

    }

    static boolean checkActionAllowed(RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS) //
		&& rrs != null
		&& rrs.isSingleSelected() //
		&& rrs.getSingleRow().isCanIssuePolicy() //
	;
    }

    // agreementNumber

    @NotNullValue(message = "Укажите номер договора")
    @NotEmptyString(message = "Укажите номер договора")
    @ValidPolicyNumber
    @ValidPolicyAgreementByNumber
    // @UniqueAgreementNumber
    private String agreementNumber;

    public String getAgreementNumber() {
	return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
	this.agreementNumber = agreementNumber;
    }

    // payerName

    @NotNullValue(message = "Укажите имя плательщика")
    private String payerName;

    public String getPayerName() {
	return payerName;
    }

    public void setPayerName(String payerName) {
	this.payerName = payerName;
    }

    // payeeTaxpayerNumber

    @NotNullValue(message = "Укажите ИИН плательщика")
    private TaxpayerNumber payeeTaxpayerNumber;

    public TaxpayerNumber getPayeeTaxpayerNumber() {
	return payeeTaxpayerNumber;
    }

    public void setPayeeTaxpayerNumber(TaxpayerNumber payeeTaxpayerNumber) {
	this.payeeTaxpayerNumber = payeeTaxpayerNumber;
    }

    // paidAmount

    @NotNullValue(message = "Укажите сумму оплаченной премии")
    @Min(value = 1, message = "Укажите сумму оплаченной премии")
    private Double paidAmount;

    public Double getPaidAmount() {
	return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
	this.paidAmount = paidAmount;
    }

    // paidInstant

    @NotNullValue(message = "Укажите дату и время оплаты")
    private Instant paidInstant;

    public Instant getPaidInstant() {
	return paidInstant;
    }

    public void setPaidInstant(Instant paidInstant) {
	this.paidInstant = paidInstant;
    }

    // paidReference

    @NotNullValue(message = "Укажите платежный референс")
    private String paidReference;

    public String getPaidReference() {
	return paidReference;
    }

    public void setPaidReference(String paidReference) {
	this.paidReference = paidReference;
    }

    // paidCurrency

    @NotNullValue(message = "Укажите валюту платежа")
    private Currency paidCurrency;

    public Currency getPaidCurrency() {
	return paidCurrency;
    }

    public void setPaidCurrency(Currency paidCurrency) {
	this.paidCurrency = paidCurrency;
    }

    // fetchedPolicy

    private Policy fetchedPolicy;

    public Policy getFetchedPolicy() {
	return fetchedPolicy;
    }

    @EJB
    private PolicyFacadeRemote policies;

    public void agreementNumberChanged(ValueChangeEvent event) {
	try {
	    fetchedPolicy = policies.getByNumber((String) event.getNewValue());
	} catch (PolicyNotFound e) {
	    throw new FacesException(e);
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}

	if (fetchedPolicy.getPaymentDate() != null) {

	    paidAmount = MyOptionals.of(fetchedPolicy)
		    .map(Policy::getActual)
		    .map(CalculationData::getAmount)
		    .orElseThrow(() -> new FacesException("Fetched policy doesn't have premium amount data"));

	    paidCurrency = MyOptionals.of(fetchedPolicy)
		    .map(Policy::getActual)
		    .map(CalculationData::getCurrency)
		    .orElseThrow(() -> new FacesException("Fetched policy doesn't have premium currency data"));

	    paidInstant = MyOptionals.of(fetchedPolicy)
		    .map(Policy::getPaymentDate)
		    .map(it -> it.atStartOfDay(ZoneId.systemDefault()))
		    .map(ZonedDateTime::toInstant)
		    .orElseThrow(() -> new FacesException("Fetched policy doesn't have date of payment"));

	    paidReference = null;

	    payerName = MyOptionals.of(fetchedPolicy)
		    .map(Policy::getInsurant)
		    .map(InsurantData::getPersonal)
		    .map(PersonalData::getFullName)
		    .orElseThrow(() -> new FacesException("Fetched policy doesn't have payer name"));

	    payeeTaxpayerNumber = MyOptionals.of(fetchedPolicy)
		    .map(Policy::getInsurant)
		    .map(InsurantData::getIdNumber)
		    .orElseThrow(() -> new FacesException("Fetched policy doesn't have id number"));

	}

    }

    // CDIs

    // local

    @Inject
    private CurrentUserHolder currentUser;

    @Inject
    private RequestsSelectionCDIBean rrs;

    // insurance-facade (remote)

    @EJB
    private InsuranceRequestFacadeRemote insuranceRequests;

    @Override
    public String doAction() throws FacesException, IllegalStateException, IllegalArgumentException {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	final InsuranceRequest ir1 = rrs.getSingleRow().getEntity();

	try {
	    InsuranceRequest ir2 = insuranceRequests.policyIssued(ir1, currentUser.getValue(), agreementNumber);
	    if (paidInstant != null) {
		ir2 = insuranceRequests.premiumPaid(ir2,
			"Введено вручную",
			paidInstant,
			paidAmount,
			paidCurrency,
			null,
			null,
			null,
			payerName);
	    }
	    rrs.setSingleRow(RequestRow.from(ir2));
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	} finally {
	    // rrs.reset();
	}
	return null;
    }
}
