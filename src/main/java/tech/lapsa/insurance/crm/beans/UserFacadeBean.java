package tech.lapsa.insurance.crm.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.insurance.facade.UserFacade.UserFacadeRemote;

@Named("userFacade")
@ApplicationScoped
public class UserFacadeBean {

    @EJB
    private UserFacadeRemote delegate;

    public List<User> getWhoEverCreatedRequests() {
	return delegate.getWhoEverCreatedRequests();
    }

}
