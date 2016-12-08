package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.lapsa.insurance.domain.crm.Manager;
import com.lapsa.insurance.persistence.dao.EntityNotFound;
import com.lapsa.insurance.persistence.dao.ManagerDAO;

import kz.theeurasia.eurasia36.beans.api.CurrentManagerHolder;

@Named("currentManager")
@ViewScoped
public class DefaultCurrentManagerHolder extends DefaultWritableValueHolder<Manager>
	implements Serializable, CurrentManagerHolder {
    private static final long serialVersionUID = 3813022087120135731L;

    private static final String DEFAULT_REMOTE_USER = "Guest";

    @Inject
    private transient Logger logger;

    @Inject
    private ManagerDAO managerDAO;

    @PostConstruct
    public void init() {
	reset();
    }

    @Override
    public void reset() {
	String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	if (remoteUser == null || remoteUser.isEmpty())
	    remoteUser = DEFAULT_REMOTE_USER;
	try {
	    value = managerDAO.findByEmail(remoteUser);
	} catch (EntityNotFound e) {
	    logger.info(String.format("New Manager creating '%1$s'", remoteUser));
	    value = new Manager();
	    value.setEmail(remoteUser);
	    value = managerDAO.save(value);
	}
    }

}
