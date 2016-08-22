package kz.theeurasia.eurasia36.beans.view;

import kz.theeurasia.eurasia36.beans.api.WritableValueHolder;

public abstract class DefaultWritableValueHolder<T> extends DefaultValueHolder<T> implements WritableValueHolder<T> {

    @Override
    public void setValue(T value) {
	this.value = value;
    }

}
