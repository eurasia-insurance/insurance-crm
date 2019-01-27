package tech.lapsa.insurance.crm.beans.actions

import static com.lapsa.insurance.elements.InsuranceRequestStatus.*
import static com.lapsa.insurance.elements.ProgressStatus.*
import static tech.lapsa.insurance.crm.auth.InsuranceSecurity.*

import javax.faces.context.ExternalContext
import javax.faces.context.FacesContext

import com.lapsa.insurance.domain.policy.PolicyRequest

import spock.lang.Specification
import spock.lang.Unroll
import tech.lapsa.insurance.crm.beans.RequestsSelectionCDIBean
import tech.lapsa.insurance.crm.beans.actions.ResumeRequestCDIBean.ResumeRequestCheckCDIBean
import tech.lapsa.insurance.crm.rows.RequestRow

class ResumeRequestCheckCDIBeanSpec extends Specification {

    FacesContext facesContext
    ExternalContext externalContext


    static allowedProgressStatus = ON_HOLD
    static allowedInsuranceRequestStatus = PENDING

    def setup() {
	facesContext = Mock()
	externalContext = Mock()
	facesContext.getExternalContext() >> externalContext
	FacesContext.setCurrentInstance(facesContext)
    }

    @Unroll
    def "Resume Request allowance is '#expAllowed' on role '#role'"() {
	given:

	externalContext.isUserInRole(role) >> true

	def policyRequest = new PolicyRequest(insuranceRequestStatus: allowedInsuranceRequestStatus, progressStatus: allowedProgressStatus)
	def rr = RequestRow.from(policyRequest)
	def rrs = new RequestsSelectionCDIBean(singleRow: rr)
	def bean = new ResumeRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	role            | expAllowed
	ROLE_ADMIN      | true
	ROLE_SPECIALIST | true
	ROLE_AGENT      | false
	ROLE_REPORTER   | false
    }

    @Unroll
    def "Resume Request allowance is '#expAllowed' on insuranfce request status '#insuranceRequestStatus'"() {
	given:

	externalContext.isUserInRole(_) >> true

	def rr = RequestRow.from(new PolicyRequest(insuranceRequestStatus: insuranceRequestStatus, progressStatus: allowedProgressStatus))
	def rrs = new RequestsSelectionCDIBean(singleRow: rr)
	def bean = new ResumeRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	insuranceRequestStatus | expAllowed
	PENDING                | true
	POLICY_ISSUED          | true
	PREMIUM_PAID           | true
	PAYMENT_CANCELED       | true
	REQUEST_CANCELED       | true
    }

    @Unroll
    def "Resume Request allowance is '#expAllowed' on progress status '#progressStatus'"() {
	given:

	externalContext.isUserInRole(_) >> true

	def rr = RequestRow.from(new PolicyRequest(insuranceRequestStatus: allowedInsuranceRequestStatus, progressStatus: progressStatus))
	def rrs = new RequestsSelectionCDIBean(singleRow: rr)
	def bean = new ResumeRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	progressStatus | expAllowed
	NEW            | false
	ON_PROCESS     | false
	ON_HOLD        | true
	FINISHED       | false
    }

    @Unroll
    def "Resume Request allowance is '#expAllowed' on selection of #number rows"() {
	given:

	externalContext.isUserInRole(_) >> true

	def r = new PolicyRequest(insuranceRequestStatus: allowedInsuranceRequestStatus, progressStatus: allowedProgressStatus)
	def rows = RequestRow.from([r]*number)

	def rrs = new RequestsSelectionCDIBean(value: rows)
	def bean = new ResumeRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	number | expAllowed
	0      | false
	1      | true
	10     | true
    }

    @Unroll
    def "Resume Request allowance is '#expAllowed' on no multiple selection where of row's status is '#insuranceRequestStatus'"() {
	given:

	externalContext.isUserInRole(_) >> true

	def rrs = new RequestsSelectionCDIBean(value: [
	    RequestRow.from(new PolicyRequest(insuranceRequestStatus: allowedInsuranceRequestStatus, progressStatus: allowedProgressStatus)),
	    RequestRow.from(new PolicyRequest(insuranceRequestStatus: allowedInsuranceRequestStatus, progressStatus: progressStatus))
	])
	def bean = new ResumeRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	progressStatus | expAllowed
	NEW            | false
	ON_PROCESS     | false
	ON_HOLD        | true
	FINISHED       | false
    }
}
