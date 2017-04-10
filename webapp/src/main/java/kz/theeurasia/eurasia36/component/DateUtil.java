package kz.theeurasia.eurasia36.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean isTodayLocalDate(LocalDate localDate) {
	return checkIsToday(localDate);
    }

    public boolean isTodayLocalDateTime(LocalDateTime localDateTime) {
	return checkIsToday(localDateTime);
    }

    public boolean isYesterday(Date date) {
	return checkIsYesterday(date);
    }

    public boolean isYesterdayLocalDate(LocalDate localDate) {
	return checkIsYesterday(localDate);
    }

    public boolean isYesterdayLocalDateTime(LocalDateTime localDateTime) {
	return checkIsYesterday(localDateTime);
    }

    public boolean isTommorow(Date date) {
	return checkIsTommorow(date);
    }

    public boolean isTommorowLocalDate(LocalDate localDate) {
	return checkIsTommorow(localDate);
    }

    public boolean isTommorowLocalDateTime(LocalDateTime localDateTime) {
	return checkIsTommorow(localDateTime);
    }

    // PUBLIC STATIC

    public static LocalDate toLocalDate(Date date) {
	if (date == null)
	    return null;
	return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDateLocalDate(LocalDate localDate) {
	if (localDate == null)
	    return null;
	return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDateLocalDateTime(LocalDateTime localDateTime) {
	if (localDateTime == null)
	    return null;
	return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static boolean checkIsToday(LocalDateTime localDateTime) {
	if (localDateTime == null)
	    return false;
	return checkIsToday(localDateTime.toLocalDate());
    }

    private static boolean checkIsToday(LocalDate localDate) {
	if (localDate == null)
	    return false;
	LocalDate check = LocalDate.now();
	return check.isEqual(localDate);
    }

    @Deprecated
    public static boolean checkIsToday(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	return checkIsToday(localDate);
    }

    @Deprecated
    public static boolean checkIsYesterday(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now().minusDays(1);
	return check.isEqual(localDate);
    }

    public static boolean checkIsYesterday(LocalDate localDate) {
	if (localDate == null)
	    return false;
	LocalDate check = LocalDate.now().minusDays(1);
	return check.isEqual(localDate);
    }

    public static boolean checkIsYesterday(LocalDateTime localDateTime) {
	if (localDateTime == null)
	    return false;
	return checkIsYesterday(localDateTime.toLocalDate());
    }

    @Deprecated
    public static boolean checkIsTommorow(Date date) {
	if (date == null)
	    return false;
	LocalDate localDate = toLocalDate(date);
	LocalDate check = LocalDate.now().plusDays(1);
	return check.isEqual(localDate);
    }

    public static boolean checkIsTommorow(LocalDate localDate) {
	if (localDate == null)
	    return false;
	LocalDate check = LocalDate.now().plusDays(1);
	return check.isEqual(localDate);
    }

    public static boolean checkIsTommorow(LocalDateTime localDateTime) {
	if (localDateTime == null)
	    return false;
	return checkIsTommorow(localDateTime.toLocalDate());
    }

}
