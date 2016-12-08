package kz.theeurasia.eurasia36.beans.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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
import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.ObtainingData;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.casco.CascoRequest;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.elements.InsuranceProductType;
import com.lapsa.insurance.elements.ObtainingMethod;
import com.lapsa.insurance.elements.PaymentMethod;
import com.lapsa.insurance.persistence.dao.CascoRequestDAO;
import com.lapsa.insurance.persistence.dao.InsuranceRequestDAO;
import com.lapsa.insurance.persistence.dao.NotPersistedException;
import com.lapsa.insurance.persistence.dao.PeristenceOperationFailed;
import com.lapsa.insurance.persistence.dao.PolicyRequestDAO;
import com.lapsa.insurance.persistence.dao.filter.InsuranceRequestFitler;

import kz.theeurasia.eurasia36.application.MainFacade;
import kz.theeurasia.eurasia36.application.UIMessages;
import kz.theeurasia.eurasia36.beans.api.CurrentManagerHolder;
import kz.theeurasia.eurasia36.beans.api.FacesMessagesFacade;
import kz.theeurasia.eurasia36.beans.api.InsuranceRequestHolder;
import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsFilterHolder;
import kz.theeurasia.eurasia36.beans.api.InsuranceRequestsHolder;
import kz.theeurasia.eurasia36.beans.view.pojo.DefaultInsuranceRequestFitler;

@Named("mainFacade")
@ApplicationScoped
public class DefaultMainFacade implements MainFacade {

    @Override
    public void onFilterChanged(AjaxBehaviorEvent event) {
	refreshRequests();
    }

    @Override
    public String doRefresh() {
	refreshRequests();
	return null;
    }

    @Override
    public String doInitialize() {
	initFilter();
	refreshRequests();
	return null;
    }

    @Override
    public String doResetFilter() {
	resetFilter();
	refreshRequests();
	return null;
    }

    public String doFilterCreatedToday() {
	filterCreatedToday();
	refreshRequests();
	return null;
    }

    public String doFilterCreatedYesterday() {
	filterCreatedYesterday();
	refreshRequests();
	return null;
    }

    public String doFilterCreatedThisWeek() {
	filterCreatedThisWeek();
	refreshRequests();
	return null;
    }

    public String doFilterCreatedLastWeek() {
	filterCreatedLastWeek();
	refreshRequests();
	return null;
    }

    public String doFilterCreatedThisMonth() {
	filterCreatedThisMonth();
	refreshRequests();
	return null;
    }

    public String doFilterCreatedLastMonth() {
	filterCreatedLastMonth();
	refreshRequests();
	return null;
    }

    @Override
    public String doAcceptRequestOnce() {
	acceptRequestOnce();
	saveRequest();
	refreshRequests();
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
	pauseRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doResumeRequest() {
	resumeRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doCloseRequest() {
	closeRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doCancelEditRequest() {
	resetRequest();
	refreshRequests();
	return null;
    }

    @Override
    public String doCompleteRequest() {
	completeRequest();
	saveRequest();
	refreshRequests();
	return null;
    }

    @Override
    public void onTransactionStatusChanged(AjaxBehaviorEvent event) {
	handleTransactionStatusChange();
    }

    @Override
    public void onObtainingMethodChanged(AjaxBehaviorEvent event) {
	handleObtainingMethodChange();
    }

    @Override
    public void onPaymentMethodChanged(AjaxBehaviorEvent event) {
	handlePaymentMethodChange();
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
    private InsuranceRequestsHolder insuranceRequestsHolder;

    @Inject
    private InsuranceRequestsFilterHolder insuranceRequestsFilterHolder;

    @Inject
    private FacesMessagesFacade facesMessagesFacade;

    @Inject
    private InsuranceRequestDAO insuranceRequestDAO;

    @Inject
    private PolicyRequestDAO policyRequestDAO;

    @Inject
    private CascoRequestDAO cascoRequestDAO;

    @Inject
    private InsuranceRequestHolder insuranceRequestHolder;

    @Inject
    private CurrentManagerHolder currentManager;

    private void initFilter() {
	resetFilter();
	insuranceRequestsFilterHolder.setRequestStatus(RequestStatus.OPEN);
    }

    private void resetFilter() {
	RequestStatus last = insuranceRequestsFilterHolder.getRequestStatus();
	insuranceRequestsFilterHolder.setValue(new DefaultInsuranceRequestFitler());
	insuranceRequestsFilterHolder.setRequestStatus(last);
    }

    private void refreshRequests() {
	InsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	InsuranceProductType productType = insuranceRequestsFilterHolder.getValue().getInsuranceProductType();
	List<InsuranceRequest> requests = null;
	if (productType == null)
	    requests = insuranceRequestDAO.findByFilter(filter);
	else
	    switch (productType) {
	    case CASCO:
		requests = new ArrayList<>();
		List<CascoRequest> cascos = cascoRequestDAO.findByFilter(filter);
		requests.addAll(cascos);
		break;
	    case POLICY:
		requests = new ArrayList<>();
		List<PolicyRequest> policies = policyRequestDAO.findByFilter(filter);
		requests.addAll(policies);
		break;
	    }
	insuranceRequestsHolder.setRequests(requests);
    }

    private void saveRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	try {
	    insuranceRequest.setUpdated(new Date());
	    InsuranceRequest insuranceRequestSaved = insuranceRequestDAO.save(insuranceRequest);
	    insuranceRequestHolder.setValue(insuranceRequestSaved);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void resetRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	try {
	    InsuranceRequest insuranceRequestSaved = insuranceRequestDAO.restore(insuranceRequest);
	    insuranceRequestHolder.setValue(insuranceRequestSaved);
	} catch (PeristenceOperationFailed e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	} catch (NotPersistedException e) {
	    facesMessagesFacade.addExceptionMessage(UIMessages.ERROR_INTERNAL_SERVER_ERROR, e);
	}
    }

    private void closeRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setClosed(new Date());
	insuranceRequest.setStatus(RequestStatus.CLOSED);
    }

    private void acceptRequestOnce() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	if (insuranceRequest.getAccepted() == null) {
	    acceptRequest();
	}
    }

    private void acceptRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_PROCESS);
	insuranceRequest.setAccepted(new Date());
	insuranceRequest.setOwner(currentManager.getValue());
    }

    private void resumeRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_PROCESS);
    }

    private void pauseRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.ON_HOLD);
    }

    private void completeRequest() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	insuranceRequest.setProgressStatus(ProgressStatus.FINISHED);
	insuranceRequest.setCompleted(new Date());
    }

    private void handleTransactionStatusChange() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	CalculationData calc = insuranceRequest.getProduct().getCalculation();

	switch (insuranceRequest.getTransactionStatus()) {
	case COMPLETED:
	    insuranceRequest.setTransactionProblem(null);
	    calc.setActualPremiumCost(calc.getCalculatedPremiumCost());
	    calc.setDiscountAmount(0d);
	    break;
	case NOT_COMPLETED:
	    calc.setActualPremiumCost(0d);
	    calc.setDiscountAmount(0d);
	    break;
	default:
	}
    }

    private void handleActualPremiumCostChange() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setDiscountAmount(calc.getCalculatedPremiumCost() - calc.getActualPremiumCost());
    }

    private void handleDiscountAmountChange() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setActualPremiumCost(calc.getCalculatedPremiumCost() - calc.getDiscountAmount());
    }

    private void setDiscountPercent(double discountPercent) {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	CalculationData calc = insuranceRequest.getProduct().getCalculation();
	calc.setDiscountAmount(calc.getCalculatedPremiumCost() * discountPercent);
	handleDiscountAmountChange();
    }

    private void handleObtainingMethodChange() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	ObtainingData obt = insuranceRequest.getObtaining();
	switch (obt.getMethod()) {
	case DELIVERY:
	case PICKUP:
	    if (obt.getStatus() == ObtainingStatus.UNDEFINED)
		obt.setStatus(null);
	    break;
	default:
	    obt.setMethod(ObtainingMethod.UNDEFINED);
	case UNDEFINED:
	    obt.setStatus(ObtainingStatus.UNDEFINED);
	    break;
	}
    }

    private void handlePaymentMethodChange() {
	InsuranceRequest insuranceRequest = insuranceRequestHolder.getValue();
	PaymentData pym = insuranceRequest.getPayment();
	switch (pym.getMethod()) {
	case PAYCARD_ONLINE:
	case PAYCASH:
	    if (pym.getStatus() == PaymentStatus.UNDEFINED)
		pym.setStatus(null);
	    break;
	default:
	    pym.setMethod(PaymentMethod.UNDEFINED);
	case UNDEFINED:
	    pym.setStatus(PaymentStatus.UNDEFINED);
	    break;
	}
    }

    private void filterCreatedToday() {
	LocalDateTime after = LocalDate.now().atStartOfDay();

	DefaultInsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(null);
    }

    private void filterCreatedYesterday() {
	LocalDateTime after = LocalDate.now().minusDays(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().atStartOfDay()
		.minus(1, ChronoUnit.SECONDS);

	DefaultInsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(fromLocalDateTime(before));
    }

    private void filterCreatedThisWeek() {
	LocalDateTime after = LocalDate.now()
		.with(ChronoField.DAY_OF_WEEK, WeekFields.ISO.getFirstDayOfWeek().getValue()).atStartOfDay();

	DefaultInsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
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

	DefaultInsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(fromLocalDateTime(before));
    }

    private void filterCreatedThisMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).atStartOfDay();

	DefaultInsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(null);
    }

    private void filterCreatedLastMonth() {
	LocalDateTime after = LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay();
	LocalDateTime before = LocalDate.now().withDayOfMonth(1).atStartOfDay().minus(1,
		ChronoUnit.SECONDS);

	DefaultInsuranceRequestFitler filter = insuranceRequestsFilterHolder.getValue();
	filter.setCreatedAfter(fromLocalDateTime(after));
	filter.setCreatedBefore(fromLocalDateTime(before));
    }

    private static Date fromLocalDateTime(LocalDateTime value) {
	return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

}
