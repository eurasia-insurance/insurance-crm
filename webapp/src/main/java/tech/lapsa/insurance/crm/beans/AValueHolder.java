package tech.lapsa.insurance.crm.beans;

import tech.lapsa.insurance.crm.beans.i.ValueHolder;

public abstract class AValueHolder<T> implements ValueHolder<T> {

    protected T value;

    @Override
    public T getValue() {
	return value;
    }

}
