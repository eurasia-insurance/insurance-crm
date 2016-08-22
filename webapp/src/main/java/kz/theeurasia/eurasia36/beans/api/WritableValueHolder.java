package kz.theeurasia.eurasia36.beans.api;

public interface WritableValueHolder<T> extends ValueHolder<T> {
    void setValue(T value);
}
