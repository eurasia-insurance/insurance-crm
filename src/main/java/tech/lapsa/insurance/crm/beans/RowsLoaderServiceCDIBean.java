package tech.lapsa.insurance.crm.beans;

import static com.lapsa.utils.security.SecurityUtils.*;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Inject;

import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.insurance.crm.auth.InsuranceRoleGroup;
import tech.lapsa.insurance.crm.beans.i.CurrentUserHolder;
import tech.lapsa.insurance.crm.beans.i.RequestType;
import tech.lapsa.insurance.crm.beans.i.SettingsHolder;
import tech.lapsa.insurance.dao.CallbackRequestDAO.CallbackRequestDAORemote;
import tech.lapsa.insurance.dao.CascoRequestDAO.CascoRequestDAORemote;
import tech.lapsa.insurance.dao.InsuranceRequestDAO.InsuranceRequestDAORemote;
import tech.lapsa.insurance.dao.ListWithStats;
import tech.lapsa.insurance.dao.PolicyRequestDAO.PolicyRequestDAORemote;
import tech.lapsa.insurance.dao.RequestDAO.RequestDAORemote;
import tech.lapsa.insurance.dao.RequestFilter;
import tech.lapsa.insurance.dao.RequestSort;
import tech.lapsa.insurance.dao.UserDAO.UserDAORemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@RequestScoped
public class RowsLoaderServiceCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // EJBs

    // insurance-dao (remote)

    @EJB
    private InsuranceRequestDAORemote insuranceRequestDAO;

    @EJB
    private RequestDAORemote requestDAO;

    @EJB
    private CallbackRequestDAORemote callbackRequestDAO;

    @EJB
    private PolicyRequestDAORemote policyRequestDAO;

    @EJB
    private CascoRequestDAORemote cascoRequestDAO;

    @EJB
    private UserDAORemote userDAO;

    // CDIs

    // local

    @Inject
    private SettingsHolder settingsHolder;

    @Inject
    private CurrentUserHolder currentUser;

    public ListWithStats<? extends Request> loadAll(final RequestSort sort) {
	return loadPart(0, Integer.MAX_VALUE, sort);
    }

    public ListWithStats<? extends Request> loadPart(int first, int pageSize, final RequestSort sort) {
	final RequestType requestType = settingsHolder.getRequestType();
	final RequestFilter requestFilter = settingsHolder.getRequestFilter();

	final RequestsFinder requestFinder = setupFinder(requestType, requestFilter, sort, first, pageSize);
	try {
	    return requestFinder.find();
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}
    }

    @FunctionalInterface
    interface RequestsFinder {
	ListWithStats<? extends Request> find() throws IllegalArgument;
    }

    private RequestsFinder setupFinder(final RequestType requestType, final RequestFilter filter,
	    final RequestSort sort, int from, int limit) {
	if (isInRole(InsuranceRoleGroup.VIEWERS_ALL)) {
	    switch (requestType) {
	    case INSURANCE_REQUEST:
		return () -> insuranceRequestDAO.findByFilterWithStats(from, limit, filter, sort);
	    case CALLBACK_REQUEST:
		return () -> callbackRequestDAO.findByFilterWithStats(from, limit, filter, sort);
	    case CASCO_REQUEST:
		return () -> cascoRequestDAO.findByFilterWithStats(from, limit, filter, sort);
	    case POLICY_REQUEST:
		return () -> policyRequestDAO.findByFilterWithStats(from, limit, filter, sort);
	    case REQUEST:
	    default:
		return () -> requestDAO.findByFilterWithStats(from, limit, filter, sort);
	    }

	} else if (isInRole(InsuranceRoleGroup.VIEWERS_GROUP_BASED)) {

	    final boolean showNoCreators;
	    final User[] uar;

	    if (currentUser.getValue().isHasGroup()) {
		// если пользователь - участник какой-либо группы
		// показываем ему авторов состоящих в его группах
		uar = currentUser.getValue() //
			.getGroups() //
			.stream() //
			.flatMap(x -> x.getMembers().stream()) //
			.toArray(User[]::new);
		// анонимов не показываем
		showNoCreators = false;
	    } else {
		// если пользователь - не состоит ни в какой группе
		// показываем ему только авторов, не состоящих ни в одной группе
		uar = userDAO.findAllWithNoGroup() //
			.stream() //
			.toArray(User[]::new);
		// показываем ему анонимов
		showNoCreators = true;
	    }

	    switch (requestType) {
	    case INSURANCE_REQUEST:
		return () -> insuranceRequestDAO.findByFilterWithStats(from, limit, filter, sort, showNoCreators,
			uar);
	    case CALLBACK_REQUEST:
		return () -> callbackRequestDAO.findByFilterWithStats(from, limit, filter, sort, showNoCreators,
			uar);
	    case CASCO_REQUEST:
		return () -> cascoRequestDAO.findByFilterWithStats(from, limit, filter, sort, showNoCreators,
			uar);
	    case POLICY_REQUEST:
		return () -> policyRequestDAO.findByFilterWithStats(from, limit, filter, sort, showNoCreators,
			uar);
	    case REQUEST:
	    default:
		return () -> requestDAO.findByFilterWithStats(from, limit, filter, sort, showNoCreators,
			uar);
	    }
	} else if (isInRole(InsuranceRoleGroup.VIEWERS_OWNED_ONLY)) {
	    switch (requestType) {
	    case INSURANCE_REQUEST:
		return () -> insuranceRequestDAO.findByFilterWithStats(from, limit, filter, sort,
			false, currentUser.getValue());
	    case CALLBACK_REQUEST:
		return () -> callbackRequestDAO.findByFilterWithStats(from, limit, filter, sort,
			false, currentUser.getValue());
	    case CASCO_REQUEST:
		return () -> cascoRequestDAO.findByFilterWithStats(from, limit, filter, sort,
			false, currentUser.getValue());
	    case POLICY_REQUEST:
		return () -> policyRequestDAO.findByFilterWithStats(from, limit, filter, sort,
			false, currentUser.getValue());
	    case REQUEST:
	    default:
		return () -> requestDAO.findByFilterWithStats(from, limit, filter, sort,
			false, currentUser.getValue());
	    }
	}

	throw new FacesException("Can not determine type of restrictions");
    }

}
