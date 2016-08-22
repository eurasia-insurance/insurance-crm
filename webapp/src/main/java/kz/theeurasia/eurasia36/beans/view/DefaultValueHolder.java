package kz.theeurasia.eurasia36.beans.view;

import kz.theeurasia.eurasia36.beans.api.ValueHolder;

public abstract class DefaultValueHolder<T> implements ValueHolder<T> {

    protected T value;

    @Override
    public T getValue() {
	return value;
    }

}
