package kz.theeurasia.eurasia36.beans.view;

import javax.annotation.PostConstruct;

import kz.theeurasia.eurasia36.beans.api.WritableValueHolder;

public abstract class DefaultWritableValueHolder<T> extends DefaultValueHolder<T> implements WritableValueHolder<T> {

    @PostConstruct
    public void init() {
	reset();
    }

    @Override
    public void setValue(T value) {
	this.value = value;
    }

}
