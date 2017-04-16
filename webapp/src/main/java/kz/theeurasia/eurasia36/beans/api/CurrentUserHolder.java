package kz.theeurasia.eurasia36.beans.api;

import com.lapsa.insurance.domain.crm.User;

public interface CurrentUserHolder extends WritableValueHolder<User> {

    boolean isCanView();

    boolean isCanViewOwnedOnly();

    boolean isCanChange();

    boolean isCanClose();

}