package kz.theeurasia.eurasia36.beans.application;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.ObtainingData;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.domain.crm.UserGroup;
import com.lapsa.insurance.elements.ObtainingStatus;
import com.lapsa.insurance.elements.PaymentStatus;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.insurance.elements.RequestStatus;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.beans.api.CurrentUserHolder;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.api.RequestHolder;
import kz.theeurasia.eurasia36.beans.api.RequestType;
import kz.theeurasia.eurasia36.beans.api.RequestsHolder;
import kz.theeurasia.eurasia36.beans.api.SettingsHolder;
import kz.theeurasia.eurasia36.beans.model.RequestsDataModelFactory;
import kz.theeurasia.eurasia36.beans.view.pojo.RequestFilterBean;
import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.dao.CallbackRequestDAO;
import tech.lapsa.insurance.dao.CascoRequestDAO;
import tech.lapsa.insurance.dao.InsuranceRequestDAO;
import tech.lapsa.insurance.dao.PolicyRequestDAO;
import tech.lapsa.insurance.dao.RequestDAO;
import tech.lapsa.insurance.dao.UserDAO;
import tech.lapsa.insurance.dao.filter.RequestFilter;
import tech.lapsa.patterns.dao.NotFound;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

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

    @FunctionalInterface
    interface FilterFinder {
	List<Request> find();
    }

    @Inject
    private UserDAO userDAO;

    private void refreshRequests() {
	checkRoleGranted(InsuranceRoleGroup.VIEWERS);

	RequestType requestType = settingsHolder.getRequestType();

	RequestFilter requestFilter = settingsHolder.getRequestFilter();

	FilterFinder f = null;

	if (isInRole(InsuranceRoleGroup.VIEWERS_ALL)) {
	    switch (requestType) {
	    case INSURANCE_REQUEST:
		f = () -> {
		    return checkedList(insuranceRequestDAO.findByFilter(requestFilter));
		};
		break;
	    case CALLBACK_REQUEST:
		f = () -> {
		    return checkedList(callbackRequestDAO.findByFilter(requestFilter));
		};
		break;
	    case CASCO_REQUEST:
		f = () -> {
		    return checkedList(cascoRequestDAO.findByFilter(requestFilter));
		};
		break;
	    case POLICY_REQUEST:
		f = () -> {
		    return checkedList(policyRequestDAO.findByFilter(requestFilter));
		};
		break;
	    case REQUEST:
	    default:
		f = () -> {
		    return checkedList(requestDAO.findByFilter(requestFilter));
		};
		break;
	    }

	} else if (isInRole(InsuranceRoleGroup.VIEWERS_GROUP_BASED)) {

	    final boolean showNoCreators;
	    List<User> restrictedToUsers = new ArrayList<>();

	    if (currentUser.getValue().isHasGroup()) {
		// если пользователь - участник какой-либо группы
		// показываем ему авторов состоящих в его группах
		for (UserGroup gg : currentUser.getValue().getGroups())
		    restrictedToUsers.addAll(gg.getMembers());
		// анонимов не показываем
		showNoCreators = false;

	    } else {
		// если пользователь - не состоит ни в какой группе
		// показываем ему только авторов, не состоящих ни в одной группе
		restrictedToUsers = userDAO.findAllWithNoGroup();
		// показываем ему анонимов
		showNoCreators = true;
	    }

	    final User[] uar = restrictedToUsers.toArray(new User[0]);

	    switch (requestType) {
	    case INSURANCE_REQUEST:
		f = () -> {
		    return checkedList(
			    insuranceRequestDAO.findByFilter(requestFilter, showNoCreators, uar));
		};
		break;
	    case CALLBACK_REQUEST:
		f = () -> {
		    return checkedList(
			    callbackRequestDAO.findByFilter(requestFilter, showNoCreators, uar));
		};
		break;
	    case CASCO_REQUEST:
		f = () -> {
		    return checkedList(
			    cascoRequestDAO.findByFilter(requestFilter, showNoCreators, uar));
		};
		break;
	    case POLICY_REQUEST:
		f = () -> {
		    return checkedList(
			    policyRequestDAO.findByFilter(requestFilter, showNoCreators, uar));
		};
		break;
	    case REQUEST:
	    default:
		f = () -> {
		    return checkedList(requestDAO.findByFilter(requestFilter, showNoCreators, uar));
		};
		break;
	    }
	} else if (isInRole(InsuranceRoleGroup.VIEWERS_OWNED_ONLY)) {
	    switch (requestType) {
	    case INSURANCE_REQUEST:
		f = () -> {
		    return checkedList(
			    insuranceRequestDAO.findByFilter(requestFilter, false, currentUser.getValue()));
		};
		break;
	    case CALLBACK_REQUEST:
		f = () -> {
		    return checkedList(
			    callbackRequestDAO.findByFilter(requestFilter, false, currentUser.getValue()));
		};
		break;
	    case CASCO_REQUEST:
		f = () -> {
		    return checkedList(cascoRequestDAO.findByFilter(requestFilter, false, currentUser.getValue()));
		};
		break;
	    case POLICY_REQUEST:
		f = () -> {
		    return checkedList(
			    policyRequestDAO.findByFilter(requestFilter, false, currentUser.getValue()));
		};
		break;
	    case REQUEST:
	    default:
		f = () -> {
		    return checkedList(requestDAO.findByFilter(requestFilter, false, currentUser.getValue()));
		};
		break;
	    }
	}

	if (f == null)
	    throw new RuntimeException("Can not determine type of restrictions");

	requestsHolder.setValue(RequestsDataModelFactory.createList(f.find()));
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> checkedList(List<? extends T> list) {
	return (List<T>) list;
    }

    private void saveRequest() {
	try {
	    Request request = requestHolder.getValue().getEntity();
	    request.setUpdated(Instant.now());
	    Request insuranceRequestSaved = requestDAO.save(request);
	    requestHolder.setValue(RequestsDataModelFactory.createRow(insuranceRequestSaved));
	} catch (Exception e) {
	    facesMessagesFacade.addExceptionMessage(e);
	}
    }

    private void resetRequest() {
	Request request = requestHolder.getValue().getEntity();
	try {
	    Request insuranceRequestSaved = requestDAO.restore(request);
	    requestHolder.setValue(RequestsDataModelFactory.createRow(insuranceRequestSaved));
	} catch (NotFound e) {
	    facesMessagesFacade.addExceptionMessage(e);
	}
    }

    private void unselectIfNotShown() {
	if (requestsHolder.getValue() == null || requestsHolder.getValue().getRowKey(requestHolder.getValue()) == null)
	    requestHolder.reset();
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

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(null);
    }

    private void filterCreatedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(before);
    }

    private void filterCreatedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
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

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(before);
    }

    private void filterCreatedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(null);
    }

    private void filterCreatedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCreatedAfter(after);
	filter.setCreatedBefore(before);
    }

    private void filterCompletedToday() {
	LocalDateTime after = LocalDate.now().atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(null);
    }

    private void filterCompletedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(before);
    }

    private void filterCompletedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
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

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(before);
    }

    private void filterCompletedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(null);
    }

    private void filterCompletedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	RequestFilterBean filter = settingsHolder.getRequestFilter();
	filter.setCompletedAfter(after);
	filter.setCompletedBefore(before);
    }

}
