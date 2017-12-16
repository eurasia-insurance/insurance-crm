package tech.lapsa.insurance.crm.beans.i;

import tech.lapsa.insurance.crm.beans.rows.RequestList;

public interface RequestsHolder extends WritableValueHolder<RequestList> {

    int getFrom();

    int getLimit();
}