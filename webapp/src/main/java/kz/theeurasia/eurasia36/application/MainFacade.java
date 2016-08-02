package kz.theeurasia.eurasia36.application;

import com.lapsa.insurance.domain.policy.PolicyRequest;

public interface MainFacade {

    String doCloseRequest(PolicyRequest order);
}
