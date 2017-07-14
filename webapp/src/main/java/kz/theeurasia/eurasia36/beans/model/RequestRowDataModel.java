package kz.theeurasia.eurasia36.beans.model;

import java.time.LocalDateTime;

import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestSource;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.domain.Request;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.localization.LocalizationLanguage;

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
    public RequestSource getRequestSource() {
	try {
	    return entity.getSource();
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
    public LocalDateTime getCreated() {
	try {
	    return entity.getCreated();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public User getCreatedBy() {
	try {
	    return entity.getCreatedBy();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public LocalDateTime getUpdated() {
	try {
	    return entity.getUpdated();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public LocalDateTime getAccepted() {
	try {
	    return entity.getAccepted();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public User getAcceptedBy() {
	try {
	    return entity.getAcceptedBy();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public LocalDateTime getCompleted() {
	try {
	    return entity.getCompleted();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public User getCompletedBy() {
	try {
	    return entity.getCompletedBy();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public LocalDateTime getClosed() {
	try {
	    return entity.getClosed();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public User getClosedBy() {
	try {
	    return entity.getClosedBy();
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
    public LocalizationLanguage getRequesterLanguage() {
	try {
	    return entity.getRequester().getPreferLanguage();
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
    public String getUTMMedium() {
	try {
	    return entity.getUtmData().getMedium();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getUTMCampaign() {
	try {
	    return entity.getUtmData().getCampaign();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public String getUTMContent() {
	try {
	    return entity.getUtmData().getContent();
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

    @Override
    public String getNote() {
	try {
	    return entity.getNote();
	} catch (NullPointerException e) {
	    return null;
	}
    }
}