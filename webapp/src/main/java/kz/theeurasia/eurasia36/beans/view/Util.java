package kz.theeurasia.eurasia36.beans.view;

import java.util.regex.Pattern;

public class Util {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isEmail(String principalName) {
	return pattern.matcher(principalName).matches();
    }

    public static String stripEmailToName(String email) {
	if (email == null)
	    return null;
	String[] verbs = email.split("\\@")[0].split("[\\.\\s]");
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < verbs.length; i++) {
	    String verb = verbs[i];
	    if (verb.length() == 0)
		continue;
	    sb.append(Character.toUpperCase(verb.charAt(0)));
	    if (verb.length() > 1)
		sb.append(verb.substring(1));
	    if (i < verbs.length - 1)
		sb.append(" ");
	}
	return sb.toString();
    }
}
