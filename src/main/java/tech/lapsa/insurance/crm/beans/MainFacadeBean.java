package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Currency;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.ObtainingData;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.elements.ObtainingStatus;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.RequestStatus;

import tech.lapsa.epayment.facade.EpaymentFacade.EpaymentFacadeRemote;
import tech.lapsa.epayment.facade.InvoiceNotFound;
import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.MainFacade;
import tech.lapsa.insurance.crm.beans.i.RequestHolder;
import tech.lapsa.insurance.crm.beans.i.SettingsHolder;
import tech.lapsa.insurance.crm.rows.RequestRow;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.insurance.dao.RequestFilter;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.patterns.dao.NotFound;

@Named("mainFacade")
@RequestScoped
public class MainFacadeBean implements MainFacade, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void onFilterChanged(AjaxBehaviorEvent event) {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	onFilterChanged();
    }

    @Override
    public void onFilterChanged() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	refreshRequests();
	unselectIfNotShown();
    }

    @Override
    public String doRefresh() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doInitialize() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	initFilter();
	refreshRequests();
	return null;
    }

    @Override
    public String doResetFilter() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	resetFilter();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedToday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCreatedToday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedYesterday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCreatedYesterday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedThisWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCreatedThisWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedLastWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCreatedLastWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedThisMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCreatedThisMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedLastMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCreatedLastMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedToday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCompletedToday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedYesterday() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCompletedYesterday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedThisWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCompletedThisWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedLastWeek() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCompletedLastWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedThisMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCompletedThisMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedLastMonth() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
	filterCompletedLastMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doAcceptRequestOnce() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	acceptRequestOnce();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public void onDatatableDblSelect(SelectEvent event) {
	acceptRequestOnce();
	saveRequest();
	refreshRequests();
    }

    @Override
    public String doPauseRequest() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	pauseRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doMarkPaidRequest() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	markPaidRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doResumeRequest() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	resumeRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doCloseRequest() {
	checkRoleGranted(InsuranceRoleGroup.CLOSERS);
	closeRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doCancelEditRequest() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	resetRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doCompleteRequest() {
	checkRoleGranted(InsuranceRoleGroup.CHANGERS);
	completeRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public void onTransactionStatusChanged(AjaxBehaviorEvent event) {
	handleTransactionStatusChange();
    }

    @Override
    public void onObtainingMethodChanged(AjaxBehaviorEvent event) {
	// DO NOTHING
    }

    @Override
    public void onActualPremiumCostChanged(AjaxBehaviorEvent event) {
	handleActualPremiumCostChange();
    }

    @Override
    public void onDiscountAmountChanged(AjaxBehaviorEvent event) {
	handleDiscountAmountChange();
    }

    @Override
    public String doSetDiscount(double discountPercent) {
	setDiscountPercent(discountPercent);
	return null;
    }

    // PRIVATE

    // EJBs

    // insurance-dao (remote)

    @EJB
    private RequestDAORemote requestDAO;

    // epayment-facade (remote)

    @EJB
    private EpaymentFacadeRemote epayments;

    // CDIs

    // local

    @Inject
    private SettingsHolder settingsHolder;

    @Inject
    private RequestHolder requestHolder;

    @Inject
    private CurrentUserHolder currentUser;

    private void initFilter() {
	resetFilter();
	settingsHolder.getRequestFilter().setRequestStatus(RequestStatus.OPEN);
    }

    private void resetFilter() {
	RequestStatus last = settingsHolder.getRequestFilter().getRequestStatus();
	settingsHolder.resetFilters();
	settingsHolder.getRequestFilter().setRequestStatus(last);
    }

    @FunctionalInterface
    interface RequestsFinder {
	List<? extends Request> find() throws IllegalArgument;
    }

    private void refreshRequests() {
	// TODO REFACT : Decide to do with this something
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);
    }

    private void saveRequest() {
	Request request = requestHolder.getValue().getEntity();
	request.setUpdated(Instant.now());
	final Request insuranceRequestSaved;
	try {
	    insuranceRequestSaved = requestDAO.save(request);
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}
	requestHolder.setValue(RequestRow.from(insuranceRequestSaved));
    }

    private void resetRequest() {
	final Request request = requestHolder.getValue().getEntity();
	try {
	    final Request insuranceRequestSaved;
	    try {
		insuranceRequestSaved = requestDAO.restore(request);
	    } catch (IllegalArgument e) {
		throw new FacesException(e);
	    }
	    requestHolder.setValue(RequestRow.from(insuranceRequestSaved));
	} catch (NotFound e) {
	    throw new IllegalStateException(e);
	}
    }

    private void unselectIfNotShown() {
	// TODO REFACT : Decide to do with this something
    }

    private void closeRequest() {
	Request request = requestHolder.getValue().getEntity();
	request.setClosed(Instant.now());
	request.setStatus(RequestStatus.CLOSED);
	request.setClosedBy(currentUser.getValue());
    }

    private void acceptRequestOnce() {
	Request request = requestHolder.getValue().getEntity();
	if (request.getAccepted() == null)
	    acceptRequest();
    }

    private void acceptRequest() {
	Request request = requestHolder.getValue().getEntity();
	request.setProgressStatus(ProgressStatus.ON_PROCESS);
	request.setAccepted(Instant.now());
	request.setAcceptedBy(currentUser.getValue());
    }

    private void resumeRequest() {
	Request request = requestHolder.getValue().getEntity();
	request.setProgressStatus(ProgressStatus.ON_PROCESS);
    }

    private void pauseRequest() {
	Request request = requestHolder.getValue().getEntity();
	request.setProgressStatus(ProgressStatus.ON_HOLD);
    }

    private void completeRequest() {
	Request request = requestHolder.getValue().getEntity();
	request.setProgressStatus(ProgressStatus.FINISHED);
	request.setCompleted(Instant.now());
	request.setCompletedBy(currentUser.getValue());
    }

    private void markPaidRequest() {
	final RequestRow<?> rr = requestHolder.getValue();

	final String invoiceNumber = rr.getPaymentInvoiceNumber();
	final Double paidAmount = requestHolder.getPaidAmount();
	final Instant paidInstant = requestHolder.getPaidInstant();
	final String paidReference = requestHolder.getPaidReference();

	try {
	    epayments.completeWithUnknownPayment(invoiceNumber, paidAmount, Currency.getInstance("KZT"), paidInstant,
		    paidReference);
	} catch (IllegalArgument | IllegalState | InvoiceNotFound e) {
	    throw new FacesException(e);
	}
    }

    private void handleTransactionStatusChange() {
	Request request = requestHolder.getValue().getEntity();
	if (!(request instanceof InsuranceRequest))
	    return;

	InsuranceRequest insuranceRequest = (InsuranceRequest) request;

	ObtainingData obt = insuranceRequest.getObtaining();
	PaymentData pym = insuranceRequest.getPayment();

	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	switch (insuranceRequest.getTransactionStatus()) {
	case COMPLETED:
	    insuranceRequest.setTransactionProblem(null);

	    obt.setStatus(ObtainingStatus.DONE);
	    pym.setStatus(PaymentStatus.DONE);

	    calc.setActualPremiumCost(calc.getCalculatedPremiumCost());
	    calc.setDiscountAmount(0d);
	    break;
	case NOT_COMPLETED:
	    obt.setStatus(ObtainingStatus.CANCELED);
	    pym.setStatus(PaymentStatus.CANCELED);

	    calc.setActualPremiumCost(0d);
	    calc.setDiscountAmount(0d);
	    break;
	default:
	}
    }

    private void handleActualPremiumCostChange() {
	Request request = requestHolder.getValue().getEntity();
	if (!(request instanceof InsuranceRequest))
	    return;
	InsuranceRequest insuranceRequest = (InsuranceRequest) request;
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setDiscountAmount(calc.getCalculatedPremiumCost() - calc.getActualPremiumCost());
    }

    private void handleDiscountAmountChange() {
	Request request = requestHolder.getValue().getEntity();
	if (!(request instanceof InsuranceRequest))
	    return;
	InsuranceRequest insuranceRequest = (InsuranceRequest) request;
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setActualPremiumCost(calc.getCalculatedPremiumCost() - calc.getDiscountAmount());
    }

    private void setDiscountPercent(double discountPercent) {
	Request request = requestHolder.getValue().getEntity();
	if (!(request instanceof InsuranceRequest))
	    return;
	InsuranceRequest insuranceRequest = (InsuranceRequest) request;
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setDiscountAmount(calc.getCalculatedPremiumCost() * discountPercent);
	handleDiscountAmountChange();
    }

    private void filterCreatedToday() {
	LocalDateTime after = LocalDate.now().atStartOfDay();

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(null);
    }

    private void filterCreatedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(before);
    }

    private void filterCreatedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(null);
    }

    private void filterCreatedLastWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).minusWeeks(1)
		.atStartOfDay();
	LocalDateTime before = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay().minus(1,
			ChronoUnit.SECONDS);

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(before);
    }

    private void filterCreatedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(null);
    }

    private void filterCreatedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(before);
    }

    private void filterCompletedToday() {
	LocalDateTime after = LocalDate.now().atStartOfDay();

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(null);
    }

    private void filterCompletedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(before);
    }

    private void filterCompletedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(null);
    }

    private void filterCompletedLastWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).minusWeeks(1)
		.atStartOfDay();
	LocalDateTime before = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay().minus(1,
			ChronoUnit.SECONDS);

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(before);
    }

    private void filterCompletedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(null);
    }

    private void filterCompletedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	RequestFilter filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(before);
    }

}
