package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.domain.crm.User;

import kz.theeurasia.eurasia36.beans.application.SecurityRole;

public interface CurrentUserHolder extends WritableValueHolder<User> {

    boolean inRole(SecurityRole role);
}