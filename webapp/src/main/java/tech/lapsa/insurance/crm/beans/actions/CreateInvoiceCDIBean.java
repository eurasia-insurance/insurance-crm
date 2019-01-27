package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.insurance.elements.InsuranceRequestStatus.PENDING;
import static com.lapsa.insurance.elements.ProgressStatus.FINISHED;
import static com.lapsa.utils.security.SecurityUtils.isInRole;

import java.io.Serializable;
import java.util.Collection;
import java.util.Currency;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.InsurantData;
import com.lapsa.insurance.domain.PersonalData;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;
import com.lapsa.international.phone.validators.ValidPhoneNumber;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.crm.validation.UniqueAgreementNumber;
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
import tech.lapsa.javax.validation.ValidEmail;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;
import tech.lapsa.kz.taxpayer.validators.ValidTaxpayerNumber;

@Named("createInvoice")
@RequestScoped
public class CreateInvoiceCDIBean implements ActionCDIBean, Serializable {

    private static final long serialVersionUID = 1L;

    @Named("createInvoiceCheck")
    @Dependent
    public static class CreateInvoiceCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public CreateInvoiceCheckCDIBean() {
	    super(CreateInvoiceCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;

    }

    private static final Predicate<RequestRow<?>> ROW_ALLOWED = rr -> rr.insuranceRequestIn(PENDING)
	    && !rr.progressIn(FINISHED);

    private static boolean checkActionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS)
		&& MyOptionals.of(rrs)
			.filter(RequestsSelectionCDIBean::isSingleSelected)
			.map(RequestsSelectionCDIBean::getSingleRow)
			.filter(ROW_ALLOWED)
			.isPresent();
    }

    // agreementNumber

    @NotNullValue(message = "Укажите номер договора")
    @NotEmptyString(message = "Укажите номер договора")
    @ValidPolicyNumber
    @ValidPolicyAgreementByNumber
    @UniqueAgreementNumber
    private String agreementNumber;

    public String getAgreementNumber() {
	return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
	this.agreementNumber = agreementNumber;
    }

    // invoicePayeeName

    @NotNullValue(message = "Введите имя плательщика")
    @NotEmptyString(message = "Введите имя плательщика")
    @Size(max = 200, message = "Макс 200 символов")
    private String invoicePayeeName;

    public String getInvoicePayeeName() {
	return invoicePayeeName;
    }

    public void setInvoicePayeeName(String invoicePayeeName) {
	this.invoicePayeeName = invoicePayeeName;
    }

    // invoiceCurrency

    @NotNullValue(message = "Укажите валюту платежа")
    private Currency invoiceCurrency;

    public Currency getInvoiceCurrency() {
	return invoiceCurrency;
    }

    public void setInvoiceCurrency(Currency invoiceCurrency) {
	this.invoiceCurrency = invoiceCurrency;
    }

    // invoiceLanguage

    @NotNullValue(message = "Укажите язык страницы оплаты")
    private LocalizationLanguage invoiceLanguage;

    public LocalizationLanguage getInvoiceLanguage() {
	return invoiceLanguage;
    }

    public void setInvoiceLanguage(LocalizationLanguage invoiceLanguage) {
	this.invoiceLanguage = invoiceLanguage;
    }

    // invoicePayeeEmail

    @NotNullValue(message = "Введите email на который будет отправлена ссылка на оплату")
    @NotEmptyString(message = "Введите email на который будет отправлена ссылка на оплату")
    @ValidEmail(message = "Введите корректный email")
    @Size(max = 200, message = "Макс 200 символов")
    private String invoicePayeeEmail;

    public String getInvoicePayeeEmail() {
	return invoicePayeeEmail;
    }

    public void setInvoicePayeeEmail(String invoicePayeeEmail) {
	this.invoicePayeeEmail = invoicePayeeEmail;
    }

    // invoicePayeePhone

    @NotNullValue(message = "Введите номер телефона")
    @ValidPhoneNumber
    private PhoneNumber invoicePayeePhone;

    public PhoneNumber getInvoicePayeePhone() {
	return invoicePayeePhone;
    }

    public void setInvoicePayeePhone(PhoneNumber invoicePayeePhone) {
	this.invoicePayeePhone = invoicePayeePhone;
    }

    // invoicePayeeTaxpayerNumber

    @NotNullValue(message = "Введите ИИН плательщика")
    @ValidTaxpayerNumber
    private TaxpayerNumber invoicePayeeTaxpayerNumber;

    public TaxpayerNumber getInvoicePayeeTaxpayerNumber() {
	return invoicePayeeTaxpayerNumber;
    }

    public void setInvoicePayeeTaxpayerNumber(TaxpayerNumber invoicePayeeTaxpayerNumber) {
	this.invoicePayeeTaxpayerNumber = invoicePayeeTaxpayerNumber;
    }

    // invoiceProductName

    @NotNullValue(message = "Укажите название страхового продукта")
    @NotEmptyString(message = "Укажите название страхового продукта")
    @Size(max = 200, message = "Макс 200 символов")
    private String invoiceProductName;

    public String getInvoiceProductName() {
	return invoiceProductName;
    }

    public void setInvoiceProductName(String invoiceProductName) {
	this.invoiceProductName = invoiceProductName;
    }

    // invoiceAmount

    @NotNullValue(message = "Укажие сумму к оплате")
    @Min(value = 1, message = "Сумма оплаты не может быть меньше 1")
    private Double invoiceAmount;

    public Double getInvoiceAmount() {
	return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
	this.invoiceAmount = invoiceAmount;
    }

    // invoiceQuantity

    @NotNullValue(message = "Укажие количество договоров")
    @Min(value = 1, message = "Количество договоров должно быть равно 1")
    @Max(value = 1, message = "Количество договоров должно быть равно 1")
    private Integer invoiceQuantity;

    public Integer getInvoiceQuantity() {
	return invoiceQuantity;
    }

    public void setInvoiceQuantity(Integer invoiceQuantity) {
	this.invoiceQuantity = invoiceQuantity;
    }

    // paidStatus

    public static enum PaidStatus {
	PAID, NOT_PAID;
    }

    @NotNullValue
    private PaidStatus paidStatus;

    public PaidStatus getPaidStatus() {
	return paidStatus;
    }

    public void setPaidStatus(PaidStatus paidStatus) {
	this.paidStatus = paidStatus;
    }

    public boolean isPaid() {
	return PaidStatus.PAID.equals(paidStatus);
    }

    public boolean isNotPaid() {
	return PaidStatus.NOT_PAID.equals(paidStatus);
    }
    //

    @EJB
    private PolicyFacadeRemote policies;

    public String doFetchPolicyAndUpdateModel() {
	fetchPolicyAndUpdateModel(agreementNumber);
	return null;
    }

    public void onAgreementNumberChanged(ValueChangeEvent event) {
	fetchPolicyAndUpdateModel((String) event.getNewValue());
    }

    private void fetchPolicyAndUpdateModel(String agreementNumber) {
	final Policy fetchedPolicy;
	try {
	    fetchedPolicy = policies.getByNumber(agreementNumber);
	} catch (PolicyNotFound e) {
	    throw new FacesException(e);
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}

	// data priority ESBD ACTUAL AMOUNT

	invoiceAmount = MyOptionals.of(fetchedPolicy)
		.map(Policy::getActual)
		.map(CalculationData::getAmount)
		.orElseThrow(() -> new FacesException("Fetched policy doesn't have premium amount data"));

	// data priority ESBD ACTUAL CURRENCY

	invoiceCurrency = MyOptionals.of(fetchedPolicy)
		.map(Policy::getActual)
		.map(CalculationData::getCurrency)
		.orElseGet(() -> Currency.getInstance("KZT"));

	// data priority REQUESTER EMAIL -> ESBD INSURANT EMAIL

	invoicePayeeEmail = MyOptionals.of(rrs)
		.map(RequestsSelectionCDIBean::getSingleRow)
		.map(RequestRow::getEntity)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getEmail)
		.orElseGet(() -> MyOptionals.of(fetchedPolicy)
			.map(Policy::getInsurant)
			.map(InsurantData::getEmail)
			.orElse(null));

	// data priority ESBD INSURANT FULL NAME -> REQUESTER NAME

	invoicePayeeName = MyOptionals.of(fetchedPolicy)
		.map(Policy::getInsurant)
		.map(InsurantData::getPersonal)
		.map(PersonalData::getFullName)
		.orElseGet(() -> MyOptionals.of(rrs)
			.map(RequestsSelectionCDIBean::getSingleRow)
			.map(RequestRow::getEntity)
			.map(InsuranceRequest::getRequester)
			.map(RequesterData::getName)
			.orElse(null));

	// data priority ESBD INSURANT IIN -> REQUESTER IIN -> ESBD
	// FIRST DRIVER IIN

	invoicePayeeTaxpayerNumber = MyOptionals.of(fetchedPolicy)
		.map(Policy::getInsurant)
		.map(InsurantData::getIdNumber)
		.orElseGet(() -> MyOptionals.of(rrs)
			.map(RequestsSelectionCDIBean::getSingleRow)
			.map(RequestRow::getEntity)
			.map(InsuranceRequest::getRequester)
			.map(RequesterData::getIdNumber)
			.orElseGet(() -> MyOptionals.of(fetchedPolicy)
				.map(Policy::getInsuredDrivers)
				.map(Collection::stream)
				.flatMap(Stream::findFirst)
				.map(PolicyDriver::getIdNumber)
				.orElse(null)));

	// data priority REQUESTER PHONE -> ESBD INSURANT PHONE

	this.invoicePayeePhone = MyOptionals.of(rrs)
		.map(RequestsSelectionCDIBean::getSingleRow)
		.map(RequestRow::getEntity)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getPhone)
		.orElseGet(() -> MyOptionals.of(fetchedPolicy)
			.map(Policy::getInsurant)
			.map(InsurantData::getPhone)
			.orElse(null));

	// data priority REQUESTER LANGUAGE

	this.invoiceLanguage = MyOptionals.of(rrs)
		.map(RequestsSelectionCDIBean::getSingleRow)
		.map(RequestRow::getEntity)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getPreferLanguage)
		.orElse(LocalizationLanguage.RUSSIAN);

	// data priority PRODUCT NAME

	this.invoiceProductName = MyOptionals.of(rrs)
		.map(RequestsSelectionCDIBean::getSingleRow)
		.map(RequestRow::getEntity)
		.map(InsuranceRequest::getProductType)
		.map(it -> it.regular(invoiceLanguage.getLocale()))
		.orElse(null);

	// CONSTANT

	this.invoiceQuantity = 1;
    }

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean rrs;

    // insurance-facade (remote)

    @EJB
    private InsuranceRequestFacadeRemote insuranceRequests;

    @Override
    public String doAction() throws FacesException, IllegalStateException, IllegalArgumentException {
	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Is invalid for unconmpleting transactions");

	final InsuranceRequest ir1 = rrs.getSingleRow().getEntity();

	try {
	    final InsuranceRequest ir2 = insuranceRequests.policyIssued(ir1, agreementNumber);
	    final InsuranceRequest ir3 = insuranceRequests.invoiceCreated(ir2,
		    invoicePayeeName,
		    invoiceCurrency,
		    invoiceLanguage,
		    invoicePayeeEmail,
		    invoicePayeePhone,
		    invoicePayeeTaxpayerNumber,
		    invoiceProductName,
		    invoiceAmount,
		    invoiceQuantity);

	    rrs.setSingleRow(RequestRow.from(ir3));
	} catch (IllegalState e1) {
	    throw e1.getRuntime();
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	}
	return null;
    }
}
