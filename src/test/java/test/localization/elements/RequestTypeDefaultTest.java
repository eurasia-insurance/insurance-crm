package test.localization.elements;

import java.util.Locale;

import kz.theeurasia.eurasia36.beans.api.RequestType;
import test.localization.ElementsLocalizationTest;

public class RequestTypeDefaultTest extends ElementsLocalizationTest<RequestType> {

    public RequestTypeDefaultTest() {
	super(RequestType.values(), RequestType.class, Locale.getDefault());
    }
}
