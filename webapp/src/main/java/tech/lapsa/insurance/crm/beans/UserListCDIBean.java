package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.insurance.facade.UserFacade.UserFacadeRemote;
import tech.lapsa.java.commons.function.MyCollections;

@Named("userList")
@SessionScoped
public class UserListCDIBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<User> whoEverCreatedRequests;
    private List<User> whoEverAcceptedRequests;
    private List<User> whoEverCompletedRequests;

    @PostConstruct
    public void init() {
	this.whoEverCreatedRequests = MyCollections.unmodifiableOrEmptyList(delegate.getWhoEverCreatedRequests());
	this.whoEverAcceptedRequests = MyCollections.unmodifiableOrEmptyList(delegate.getWhoEverAcceptedRequests());
	this.whoEverCompletedRequests = MyCollections.unmodifiableOrEmptyList(delegate.getWhoEverCompletedRequests());
    }

    @EJB
    private UserFacadeRemote delegate;

    public List<User> getWhoEverCreatedRequests() {
	return whoEverCreatedRequests;
    }

    public List<User> getWhoEverAcceptedRequests() {
	return whoEverAcceptedRequests;
    }

    public List<User> getWhoEverCompletedRequests() {
	return whoEverCompletedRequests;
    }
}
