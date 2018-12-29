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

public interface UserListCDIBeans extends Serializable {

    public static interface UserList {

	List<User> getList();
    }

    @Named("usersWhoEverCreatedRequests")
    @SessionScoped
    public static class UsersWhoEverCreatedRequestsCDIBean implements Serializable, UserList {

	private static final long serialVersionUID = 1L;

	private List<User> list;

	@PostConstruct
	public void init() {
	    this.list = MyCollections.unmodifiableOrEmptyList(delegate.getWhoEverCreatedRequests());
	}

	@EJB
	private UserFacadeRemote delegate;

	@Override
	public List<User> getList() {
	    return list;
	}
    }

    @Named("usersWhoEverPickedRequests")
    @SessionScoped
    public static class UsersWhoEverPickedRequestsCDIBean implements Serializable, UserList {

	private static final long serialVersionUID = 1L;

	private List<User> list;

	@PostConstruct
	public void init() {
	    this.list = MyCollections.unmodifiableOrEmptyList(delegate.getWhoEverPickedRequests());
	}

	@EJB
	private UserFacadeRemote delegate;

	@Override
	public List<User> getList() {
	    return list;
	}
    }

    @Named("usersWhoEverCompletedRequests")
    @SessionScoped
    public static class UsersWhoEverCompletedRequestsCDIBean implements Serializable, UserList {

	private static final long serialVersionUID = 1L;

	private List<User> list;

	@PostConstruct
	public void init() {
	    this.list = MyCollections.unmodifiableOrEmptyList(delegate.getWhoEverCompletedRequests());
	}

	@EJB
	private UserFacadeRemote delegate;

	@Override
	public List<User> getList() {
	    return list;
	}
    }

    @Named("usersAll")
    @SessionScoped
    public static class UsersAllCDIBean implements Serializable, UserList {

	private static final long serialVersionUID = 1L;

	private List<User> list;

	@PostConstruct
	public void init() {
	    this.list = MyCollections.unmodifiableOrEmptyList(delegate.getAll());
	}

	@EJB
	private UserFacadeRemote delegate;

	@Override
	public List<User> getList() {
	    return list;
	}
    }
}
