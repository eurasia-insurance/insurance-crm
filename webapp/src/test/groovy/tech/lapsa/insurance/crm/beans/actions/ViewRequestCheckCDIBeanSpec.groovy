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
import tech.lapsa.insurance.crm.beans.actions.ViewRequestCDIBean.ViewRequestCheckCDIBean
import tech.lapsa.insurance.crm.rows.RequestRow

class ViewRequestCheckCDIBeanSpec extends Specification {

    FacesContext facesContext
    ExternalContext externalContext

    def setup() {
	facesContext = Mock()
	externalContext = Mock()
	facesContext.getExternalContext() >> externalContext
	FacesContext.setCurrentInstance(facesContext)
    }

    @Unroll
    def "View Request allowance is '#expAllowed' on role '#role'"() {
	given:

	externalContext.isUserInRole(role) >> true

	def policyRequest = new PolicyRequest()
	def rr = RequestRow.from(policyRequest)
	def rrs = new RequestsSelectionCDIBean(singleRow: rr)
	def bean = new ViewRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	role            | expAllowed
	ROLE_ADMIN      | true
	ROLE_SPECIALIST | true
	ROLE_AGENT      | true
	ROLE_REPORTER   | false
    }

    @Unroll
    def "View Request allowance is '#expAllowed' on selection of #number rows"() {
	given:

	externalContext.isUserInRole(_) >> true

	def r = new PolicyRequest()
	def rows = RequestRow.from([r]*number)

	def rrs = new RequestsSelectionCDIBean(value: rows)
	def bean = new ViewRequestCheckCDIBean(rrs: rrs)

	when:
	def allowed = bean.allowed

	then:
	allowed == expAllowed

	where:
	number | expAllowed
	0      | false
	1      | true
	10     | false
    }
}
