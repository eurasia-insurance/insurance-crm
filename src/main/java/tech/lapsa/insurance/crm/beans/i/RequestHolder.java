package tech.lapsa.insurance.crm.beans.i;

import java.time.Instant;

import tech.lapsa.insurance.crm.rows.RequestRow;

public interface RequestHolder extends WritableValueHolder<RequestRow<?>> {

    // TODO REFACT :: Move to RequestRow
    Double getPaidAmount();

    Instant getPaidInstant();

    String getPaidReference();
}
