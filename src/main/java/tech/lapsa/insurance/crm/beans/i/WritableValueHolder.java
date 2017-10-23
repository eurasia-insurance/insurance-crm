package tech.lapsa.insurance.crm.beans.i;

public interface WritableValueHolder<T> extends ValueHolder<T> {
    void reset();

    void setValue(T value);
}
