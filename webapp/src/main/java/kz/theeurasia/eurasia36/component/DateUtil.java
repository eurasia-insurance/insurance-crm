package kz.theeurasia.eurasia36.component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("crmDateUtil")
@ApplicationScoped
public class DateUtil {

    public boolean isToday(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now();
	return check.isEqual(localDate);
    }

    public boolean isYesterday(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now().minusDays(1);
	return check.isEqual(localDate);
    }

    public boolean isTommorow(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now().plusDays(1);
	return check.isEqual(localDate);
    }

    // PUBLIC STATIC

    public static LocalDate toLocalDate(Date date) {
	if (date == null)
	    return null;
	return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
