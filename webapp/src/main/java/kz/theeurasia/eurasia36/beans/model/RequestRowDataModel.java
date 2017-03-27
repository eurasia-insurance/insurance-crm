package kz.theeurasia.eurasia36.beans.model;

import java.util.Date;

import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.Request;

public abstract class RequestRowDataModel<T extends Request> implements RequestRow<T> {

    protected final T entity;

    public RequestRowDataModel(T entity) {
	this.entity = entity;
    }

    @Override
    public T getEntity() {
	return entity;
    }

    @Override
    public Integer getId() {
	try {
	    return entity.getId();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public RequestStatus getRequestStatus() {
	try {
	    return entity.getStatus();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public ProgressStatus getProgressStatus() {
	try {
	    return entity.getProgressStatus();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Date getCreated() {
	try {
	    return entity.getCreated();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Date getUpdated() {
	try {
	    return entity.getUpdated();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getRequesterName() {
	try {
	    return entity.getRequester().getName();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getRequesterEmail() {
	try {
	    return entity.getRequester().getEmail();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getRequesterPhone() {
	try {
	    return entity.getRequester().getPhone().getFormatted();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getRequesterIdNumber() {
	try {
	    return entity.getRequester().getIdNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getUTMSource() {
	try {
	    return entity.getUtmData().getSource();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getUTMTerm() {
	try {
	    return entity.getUtmData().getTerm();
	} catch (NullPointerException e) {
	    return null;
	}
    }

}