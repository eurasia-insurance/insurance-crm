package kz.theeurasia.policy.calc.facade.messages.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

import kz.theeurasia.eurasia36.application.UIMessages;

public class UIMessagesTest {

    @Test
    public void testRussianBundle() {
	ResourceBundle resources = ResourceBundle.getBundle(UIMessages.BUNDLE_BASENAME, Locale.forLanguageTag("ru"));
	testBundle(resources);
    }

    private void testBundle(ResourceBundle resources) {
	assertThat(resources, not(nullValue()));
	for (UIMessages c : UIMessages.values()) {
	    try {
		resources.getString(c.getKey());
	    } catch (MissingResourceException e) {
		fail(String.format("Missing key %1$s", c.getKey()));
	    }
	}
    }
}
