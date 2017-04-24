package kz.theeurasia.policy.calc.facade.messages.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import kz.theeurasia.eurasia36.application.UIMessages;

public class ResourceBundleTest {

    @Test
    public void testNoExcessRecordsRussian() {
	Locale locale = Locale.forLanguageTag("ru");
	assertThat(locale.getLanguage(), allOf(not(nullValue()), is("ru")));
	ResourceBundle resources = ResourceBundle.getBundle(UIMessages.BUNDLE_BASENAME, locale);
	assertThat(resources, not(nullValue()));
	assertThat(resources.getString("name.company-short"), allOf(not(nullValue()), is("СК Евразия")));
	testBundle(resources);
    }

    private UIMessages byKey(String key) {
	for (UIMessages ui : UIMessages.values())
	    if (ui.getKey().equals(key))
		return ui;
	return null;
    }

    private void testBundle(ResourceBundle resources) {
	Enumeration<String> keys = resources.getKeys();
	while (keys.hasMoreElements()) {
	    String key = keys.nextElement();
	    UIMessages ui = byKey(key);
	    assertThat(String.format("Resource bunddle key '%1$s' is not present", key), ui, not(nullValue()));
	    assertThat(String.format("Enumeration '%1$s.%2$s' wrong key '%2$s'", UIMessages.class.getName(), ui.name(),
		    ui.getKey()), ui.getKey(), is(key));
	}
    }
}
