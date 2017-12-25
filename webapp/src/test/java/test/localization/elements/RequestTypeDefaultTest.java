package test.localization.elements;

import java.util.Locale;

import tech.lapsa.insurance.crm.beans.i.RequestType;
import test.localization.ElementsLocalizationTest;

public class RequestTypeDefaultTest extends ElementsLocalizationTest<RequestType> {

    public RequestTypeDefaultTest() {
	super(RequestType.values(), RequestType.class, Locale.getDefault());
    }
}
