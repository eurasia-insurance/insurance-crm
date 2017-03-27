package kz.theeurasia.eurasia36.beans.model;

import java.util.Date;

import com.lapsa.insurance.crm.ProgressStatus;
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
	return entity.getId();
    }

    @Override
    public ProgressStatus getProgressStatus() {
	return entity.getProgressStatus();
    }

    @Override
    public Date getCreated() {
	return entity.getCreated();
    }

    @Override
    public Date getUpdated() {
	return entity.getUpdated();
    }

    @Override
    public String getRequesterName() {
	return entity.getRequester().getName();
    }

    @Override
    public String getRequesterEmail() {
	return entity.getRequester().getEmail();
    }

    @Override
    public String getRequesterPhone() {
	return entity.getRequester().getPhone().getFormatted();
    }

    @Override
    public String getRequesterIdNumber() {
	return entity.getRequester().getIdNumber();
    }

    @Override
    public String getUTMSource() {
	return entity.getUtmData().getSource();
    }

    @Override
    public String getUTMTerm() {
	return entity.getUtmData().getTerm();
    }
}
