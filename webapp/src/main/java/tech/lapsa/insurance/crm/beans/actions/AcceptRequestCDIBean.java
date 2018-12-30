package tech.lapsa.insurance.crm.beans.actions;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.util.Currency;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceProduct;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;
import com.lapsa.international.phone.validators.ValidPhoneNumber;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.facade.InsuranceRequestFacade.InsuranceRequestFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.javax.validation.NotEmptyString;
import tech.lapsa.javax.validation.NotNullValue;
import tech.lapsa.javax.validation.ValidEmail;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;
import tech.lapsa.kz.taxpayer.validators.ValidTaxpayerNumber;

@Named("acceptRequest")
@RequestScoped
public class AcceptRequestCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Named("acceptRequestCheck")
    @Dependent
    public static class AcceptRequestCheckCDIBean
	    extends AActionChecker
	    implements Serializable {

	public AcceptRequestCheckCDIBean() {
	    super(AcceptRequestCDIBean::checkActionAllowed);
	}

	private static final long serialVersionUID = 1L;
    }

    static boolean checkActionAllowed(final RequestsSelectionCDIBean rrs) {
	return isInRole(InsuranceRoleGroup.CHANGERS) //
		&& rrs != null
		&& rrs.isSingleSelected() //
		&& rrs.getSingleRow().isCanAccept();
    }

    // CDIs

    // local

    @Inject
    private RequestsSelectionCDIBean rrs;

    // EJBs

    // insurance-facade (remote)

    @EJB
    private InsuranceRequestFacadeRemote insuranceRequests;

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

    public String doAccept() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);

	rrs.refresh();

	if (!checkActionAllowed(rrs))
	    throw MyExceptions.format(FacesException::new, "Accepting is unavailable");

	try {
	    InsuranceRequest request = (InsuranceRequest) rrs.getSingleRow().getEntity();
	    InsuranceRequest result = insuranceRequests.acceptRequest(request, invoicePayeeName, invoiceCurrency, invoiceLanguage,
		    invoicePayeeEmail, invoicePayeePhone, invoicePayeeTaxpayerNumber, invoiceProductName, invoiceAmount,
		    invoiceQuantity);
	    rrs.setSingleRow(RequestRow.from(result));
	} catch (IllegalArgument e1) {
	    throw e1.getRuntime();
	} finally {
	    // rrs.reset();
	}

	return null;
    }

    @PostConstruct
    public void init() {
	InsuranceRequest r = (InsuranceRequest) rrs.getSingleRow().getEntity();

	this.invoicePayeeName = MyOptionals.of(r)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getName)
		.orElse(null);

	this.invoiceCurrency = MyOptionals.of(r)
		.map(InsuranceRequest::getProduct)
		.map(InsuranceProduct::getCalculation)
		.map(CalculationData::getCurrency)
		.orElseGet(() -> Currency.getInstance("KZT"));

	this.invoiceLanguage = MyOptionals.of(r)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getPreferLanguage)
		.orElse(LocalizationLanguage.RUSSIAN);

	this.invoicePayeeEmail = MyOptionals.of(r)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getEmail)
		.orElse(null);

	this.invoicePayeePhone = MyOptionals.of(r)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getPhone)
		.orElse(null);

	this.invoicePayeeTaxpayerNumber = MyOptionals.of(r)
		.map(InsuranceRequest::getRequester)
		.map(RequesterData::getIdNumber)
		.orElse(null);

	this.invoiceProductName = MyOptionals.of(r)
		.map(InsuranceRequest::getProductType)
		.map(it -> it.regular(invoiceLanguage.getLocale()))
		.orElse(null);

	this.invoiceAmount = MyOptionals.of(r)
		.map(InsuranceRequest::getProduct)
		.map(InsuranceProduct::getCalculation)
		.map(CalculationData::getAmount)
		.orElse(null);

	this.invoiceQuantity = 1;

    }
}
