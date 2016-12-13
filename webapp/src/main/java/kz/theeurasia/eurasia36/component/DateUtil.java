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
	return checkIsToday(date);
    }

    public boolean isYesterday(Date date) {
	return checkIsYesterday(date);
    }

    public boolean isTommorow(Date date) {
	return checkIsTommorow(date);
    }

    // PUBLIC STATIC

    public static LocalDate toLocalDate(Date date) {
	if (date == null)
	    return null;
	return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean checkIsToday(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now();
	return check.isEqual(localDate);
    }

    public static boolean checkIsYesterday(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now().minusDays(1);
	return check.isEqual(localDate);
    }

    public static boolean checkIsTommorow(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now().plusDays(1);
	return check.isEqual(localDate);
    }

}
