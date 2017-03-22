package kz.theeurasia.eurasia36.beans.view.pojo;

import java.io.Serializable;
import java.util.Date;

import com.lapsa.insurance.crm.ProgressStatus;
import com.lapsa.insurance.crm.RequestStatus;
import com.lapsa.insurance.dao.filter.RequestFilter;
import com.lapsa.insurance.domain.crm.User;
import com.lapsa.insurance.elements.InsuranceProductType;

public class RequestFilterBean implements RequestFilter, Serializable {
    private static final long serialVersionUID = -5052366661196023039L;

    private Integer id;
    private String requesterNameMask;
    private String requesterIdNumberMask;

    private RequestStatus requestStatus;
    private InsuranceProductType insuranceProductType;
    private ProgressStatus progressStatus;

    private Date createdAfter;
    private Date createdBefore;
    private Date completedAfter;
    private Date completedBefore;

    private User acceptedBy;
    private User completedBy;

    @Override
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Override
    public String getRequesterNameMask() {
	return requesterNameMask;
    }

    public void setRequesterNameMask(String requesterNameMask) {
	this.requesterNameMask = requesterNameMask;
    }

    @Override
    public String getRequesterIdNumberMask() {
	return requesterIdNumberMask;
    }

    public void setRequesterIdNumberMask(String requesterIdNumberMask) {
	this.requesterIdNumberMask = requesterIdNumberMask;
    }

    @Override
    public RequestStatus getRequestStatus() {
	return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
	this.requestStatus = requestStatus;
    }

    public InsuranceProductType getInsuranceProductType() {
	return insuranceProductType;
    }

    public void setInsuranceProductType(InsuranceProductType insuranceProductType) {
	this.insuranceProductType = insuranceProductType;
    }

    @Override
    public ProgressStatus getProgressStatus() {
	return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
	this.progressStatus = progressStatus;
    }

    @Override
    public Date getCreatedAfter() {
	return createdAfter;
    }

    public void setCreatedAfter(Date createdAfter) {
	this.createdAfter = createdAfter;
    }

    @Override
    public Date getCreatedBefore() {
	return createdBefore;
    }

    public void setCreatedBefore(Date createdBefore) {
	this.createdBefore = createdBefore;
    }

    @Override
    public Date getCompletedAfter() {
	return completedAfter;
    }

    public void setCompletedAfter(Date completedAfter) {
	this.completedAfter = completedAfter;
    }

    @Override
    public Date getCompletedBefore() {
	return completedBefore;
    }

    public void setCompletedBefore(Date completedBefore) {
	this.completedBefore = completedBefore;
    }

    @Override
    public User getAcceptedBy() {
	return acceptedBy;
    }

    public void setAcceptedBy(User acceptedBy) {
	this.acceptedBy = acceptedBy;
    }

    @Override
    public User getCompletedBy() {
	return completedBy;
    }

    public void setCompletedBy(User completedBy) {
	this.completedBy = completedBy;
    }
}
