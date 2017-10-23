package tech.lapsa.insurance.crm.beans;

import javax.annotation.PostConstruct;

import tech.lapsa.insurance.crm.beans.i.WritableValueHolder;

public abstract class AWritableValueHolder<T> extends AValueHolder<T> implements WritableValueHolder<T> {

    @PostConstruct
    public void init() {
	reset();
    }

    @Override
    public void setValue(T value) {
	this.value = value;
    }

}
