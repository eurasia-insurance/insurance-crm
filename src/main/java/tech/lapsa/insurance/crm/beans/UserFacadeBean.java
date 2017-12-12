package tech.lapsa.insurance.crm.beans;

import static tech.lapsa.java.commons.function.MyExceptions.*;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.insurance.facade.EJBViaCDI;
import tech.lapsa.insurance.facade.UserFacade;

@Named("userFacade")
@ApplicationScoped
public class UserFacadeBean {

    @Inject
    @EJBViaCDI
    private UserFacade delegate;

    public List<User> getWhoEverCreatedRequests() {
	return reThrowAsUnchecked(() -> delegate.getWhoEverCreatedRequests());
    }

}
