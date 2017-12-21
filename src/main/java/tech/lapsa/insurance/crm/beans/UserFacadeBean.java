package tech.lapsa.insurance.crm.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.insurance.facade.UserFacade.UserFacadeRemote;

@Named("userFacade")
@RequestScoped
public class UserFacadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserFacadeRemote delegate;

    public List<User> getWhoEverCreatedRequests() {
	return delegate.getWhoEverCreatedRequests();
    }

}
