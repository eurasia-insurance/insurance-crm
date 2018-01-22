package tech.lapsa.insurance.crm.beans.i;

import com.lapsa.insurance.domain.crm.User;

public interface CurrentUserHolder extends WritableValueHolder<User> {

    boolean isCanView();

    boolean isCanChange();

    boolean isCanClose();

    boolean isCanDelete();
}