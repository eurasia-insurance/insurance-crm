package tech.lapsa.insurance.crm.rows;

import java.time.Instant;

import com.lapsa.insurance.domain.InsuranceRequest;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.insurance.domain.crm.UTMData;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.ProgressStatus;
import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.java.commons.function.MyOptionals;

public abstract class RequestRowDataModel<T extends InsuranceRequest> implements RequestRow<T> {

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
    public boolean isArchived() {
	return entity.getArchived();
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
    public boolean progressIn(ProgressStatus... statuses) {
	return MyOptionals.of(entity).filter(it -> it.progressStatusIn(statuses)).isPresent();
    }

    @Override
    public Instant getCreated() {
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
    public Instant getUpdated() {
	try {
	    return entity.getUpdated();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Instant getPicked() {
	try {
	    return entity.getPicked();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public User getPickedBy() {
	try {
	    return entity.getPickedBy();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public Instant getCompleted() {
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
	    return entity.getRequester().getIdNumber().getNumber();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public RequesterData getRequester() {
	try {
	    return entity.getRequester();
	} catch (NullPointerException e) {
	    return null;
	}
    }

    @Override
    public UTMData getUtm() {
	try {
	    return entity.getUtmData();
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
	    return entity.getNote().trim();
	} catch (NullPointerException e) {
	    return null;
	}
    }
}