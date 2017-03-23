package kz.theeurasia.eurasia36.beans.application;

import static kz.theeurasia.eurasia36.beans.application.AuthorizationUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.lapsa.insurance.crm.ObtainingStatus;
import com.lapsa.insurance.crm.PaymentStatus;
import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.dao.CallbackRequestDAO;
import com.lapsa.insurance.dao.CascoRequestDAO;
import com.lapsa.insurance.dao.InsuranceRequestDAO;
import com.lapsa.insurance.dao.NotPersistedException;
import com.lapsa.insurance.dao.PeristenceOperationFailed;
import com.lapsa.insurance.dao.PolicyRequestDAO;
import com.lapsa.insurance.dao.RequestDAO;
import com.lapsa.insurance.dao.filter.InsuranceRequestFilter;
import com.lapsa.insurance.dao.filter.RequestFilter;
import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.ObtainingData;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.Request;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.CurrentUserHolder;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.api.RequestHolder;
import kz.theeurasia.eurasia36.beans.api.RequestType;
import kz.theeurasia.eurasia36.beans.api.RequestsHolder;
import kz.theeurasia.eurasia36.beans.api.SettingsHolder;
import kz.theeurasia.eurasia36.beans.view.pojo.RequestFilterBean;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    private final static SecurityRole[] VIEW = new SecurityRole[] { SecurityRole.REPORTER, SecurityRole.SPECIALIST,
	    SecurityRole.SUPERVISOR };

    private final static SecurityRole[] CHANGE = new SecurityRole[] { SecurityRole.SPECIALIST,
	    SecurityRole.SUPERVISOR };

    private final static SecurityRole[] CLOSE = new SecurityRole[] {
	    SecurityRole.SUPERVISOR };

    @Override
    public void onFilterChanged(AjaxBehaviorEvent event) {
	onFilterChanged();
    }

    @Override
    public void onFilterChanged() {
	refreshRequests();
	unselectIfNotShown();
    }

    @Override
    public String doRefresh() {
	checkRoleAllowed(VIEW);
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doInitialize() {
	checkRoleAllowed(VIEW);
	initFilter();
	refreshRequests();
	return null;
    }

    @Override
    public String doResetFilter() {
	checkRoleAllowed(VIEW);
	resetFilter();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedToday() {
	checkRoleAllowed(VIEW);
	filterCreatedToday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedYesterday() {
	checkRoleAllowed(VIEW);
	filterCreatedYesterday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedThisWeek() {
	checkRoleAllowed(VIEW);
	filterCreatedThisWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedLastWeek() {
	checkRoleAllowed(VIEW);
	filterCreatedLastWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedThisMonth() {
	checkRoleAllowed(VIEW);
	filterCreatedThisMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCreatedLastMonth() {
	checkRoleAllowed(VIEW);
	filterCreatedLastMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedToday() {
	checkRoleAllowed(VIEW);
	filterCompletedToday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedYesterday() {
	checkRoleAllowed(VIEW);
	filterCompletedYesterday();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedThisWeek() {
	checkRoleAllowed(VIEW);
	filterCompletedThisWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedLastWeek() {
	checkRoleAllowed(VIEW);
	filterCompletedLastWeek();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedThisMonth() {
	checkRoleAllowed(VIEW);
	filterCompletedThisMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doFilterCompletedLastMonth() {
	checkRoleAllowed(VIEW);
	filterCompletedLastMonth();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doAcceptRequestOnce() {
	checkRoleAllowed(CHANGE);
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
	checkRoleAllowed(CHANGE);
	pauseRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doResumeRequest() {
	checkRoleAllowed(CHANGE);
	resumeRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doCloseRequest() {
	checkRoleAllowed(CLOSE);
	closeRequest();
	saveRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doCancelEditRequest() {
	checkRoleAllowed(CHANGE);
	resetRequest();
	refreshRequests();
	unselectIfNotShown();
	return null;
    }

    @Override
    public String doCompleteRequest() {
	checkRoleAllowed(CHANGE);
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
    public void onPaymentMethodChanged(AjaxBehaviorEvent event) {
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

    @Inject
    private RequestsHolder requestsHolder;

    @Inject
    private SettingsHolder settingsHolder;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Inject
    private InsuranceRequestDAO insuranceRequestDAO;

    @Inject
    private RequestDAO requestDAO;

    @Inject
    private CallbackRequestDAO callbackRequestDAO;

    @Inject
    private PolicyRequestDAO policyRequestDAO;

    @Inject
    private CascoRequestDAO cascoRequestDAO;

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

    private void refreshRequests() {
	RequestType requestType = settingsHolder.getRequestType();

	RequestFilter requestFilter = settingsHolder.getRequestFilter();
	InsuranceRequestFilter insuranceRequestFilter = settingsHolder.getInsuranceRequestFilter();

	List<Request> requests = null;

	switch (requestType) {
	case INSURANCE_REQUEST:
	    requests = Collections
		    .unmodifiableList(insuranceRequestDAO.findByFilter(requestFilter, insuranceRequestFilter));
	    break;
	case CALLBACK_REQUEST:
	    requests = Collections.unmodifiableList(callbackRequestDAO.findByFilter(requestFilter));
	    break;
	case CASCO_REQUEST:
	    requests = Collections
		    .unmodifiableList(cascoRequestDAO.findByFilter(requestFilter, insuranceRequestFilter));
	    break;
	case POLICY_REQUEST:
	    requests = Collections
		    .unmodifiableList(policyRequestDAO.findByFilter(requestFilter, insuranceRequestFilter));
	    break;
	case REQUEST:
	default:
	    requests = Collections.unmodifiableList(requestDAO.findByFilter(requestFilter));
	    break;
	}

	requestsHolder.setRequests(requests);
    }

    private void saveRequest() {
	Request request = requestHolder.getValue();
	try {
	    request.setUpdated(new Date());
	    Request insuranceRequestSaved = requestDAO.save(request);
	    requestHolder.setValue(insuranceRequestSaved);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void resetRequest() {
	Request request = requestHolder.getValue();
	try {
	    Request insuranceRequestSaved = requestDAO.restore(request);
	    requestHolder.setValue(insuranceRequestSaved);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	} catch (NotPersistedException e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void unselectIfNotShown() {
	Request request = requestHolder.getValue();
	List<Request> requests = requestsHolder.getValue();
	if (request != null && requests != null && !requests.contains(request))
	    requestHolder.reset();

    }

    private void closeRequest() {
	Request insuranceRequest = requestHolder.getValue();
	insuranceRequest.setClosed(new Date());
	insuranceRequest.setStatus(RequestStatus.CLOSED);
	insuranceRequest.setClosedBy(currentUser.getValue());
    }

    private void acceptRequestOnce() {
	Request insuranceRequest = requestHolder.getValue();
	if (insuranceRequest.getAccepted() == null)
	    acceptRequest();
    }

    private void acceptRequest() {
	Request insuranceRequest = requestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_PROCESS);
	insuranceRequest.setAccepted(new Date());
	insuranceRequest.setAcceptedBy(currentUser.getValue());
    }

    private void resumeRequest() {
	Request insuranceRequest = requestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_PROCESS);
    }

    private void pauseRequest() {
	Request insuranceRequest = requestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_HOLD);
    }

    private void completeRequest() {
	Request insuranceRequest = requestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.FINISHED);
	insuranceRequest.setCompleted(new Date());
	insuranceRequest.setCompletedBy(currentUser.getValue());
    }

    private void handleTransactionStatusChange() {
	Request request = requestHolder.getValue();
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
	Request request = requestHolder.getValue();
	if (!(request instanceof InsuranceRequest))
	    return;
	InsuranceRequest insuranceRequest = (InsuranceRequest) request;
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setDiscountAmount(calc.getCalculatedPremiumCost() - calc.getActualPremiumCost());
    }

    private void handleDiscountAmountChange() {
	Request request = requestHolder.getValue();
	if (!(request instanceof InsuranceRequest))
	    return;
	InsuranceRequest insuranceRequest = (InsuranceRequest) request;
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setActualPremiumCost(calc.getCalculatedPremiumCost() - calc.getDiscountAmount());
    }

    private void setDiscountPercent(double discountPercent) {
	Request request = requestHolder.getValue();
	if (!(request instanceof InsuranceRequest))
	    return;
	InsuranceRequest insuranceRequest = (InsuranceRequest) request;
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setDiscountAmount(calc.getCalculatedPremiumCost() * discountPercent);
	handleDiscountAmountChange();
    }

    private void filterCreatedToday() {
	LocalDateTime after = LocalDate.now().atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(null);
    }

    private void filterCreatedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(fromLocalDateTime(before));
    }

    private void filterCreatedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(null);
    }

    private void filterCreatedLastWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).minusWeeks(1)
		.atStartOfDay();
	LocalDateTime before = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay().minus(1,
			ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(fromLocalDateTime(before));
    }

    private void filterCreatedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(null);
    }

    private void filterCreatedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(fromLocalDateTime(before));
    }

    private void filterCompletedToday() {
	LocalDateTime after = LocalDate.now().atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(fromLocalDateTime(after));
	filter.setCompletedBefore(null);
    }

    private void filterCompletedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(fromLocalDateTime(after));
	filter.setCompletedBefore(fromLocalDateTime(before));
    }

    private void filterCompletedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(fromLocalDateTime(after));
	filter.setCompletedBefore(null);
    }

    private void filterCompletedLastWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).minusWeeks(1)
		.atStartOfDay();
	LocalDateTime before = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay().minus(1,
			ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(fromLocalDateTime(after));
	filter.setCompletedBefore(fromLocalDateTime(before));
    }

    private void filterCompletedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(fromLocalDateTime(after));
	filter.setCompletedBefore(null);
    }

    private void filterCompletedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(fromLocalDateTime(after));
	filter.setCompletedBefore(fromLocalDateTime(before));
    }

    private static Date fromLocalDateTime(LocalDateTime value) {
	return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

}
