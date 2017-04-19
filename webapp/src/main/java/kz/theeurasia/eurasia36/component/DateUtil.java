package kz.theeurasia.eurasia36.component;

import static com.lapsa.utils.TemporalUtils.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("crmDateUtil")
@ApplicationScoped
public class DateUtil {

    public Date toDateLocalDate(LocalDate localDate) {
	return toDate(localDate);
    }

    public Date toDateLocalDateTime(LocalDateTime localDateTime) {
	return toDate(localDateTime);
    }

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

    private static boolean checkIsToday(LocalDateTime localDateTime) {
	if (localDateTime == null)
	    return false;
	return checkIsToday(toLocalDate(localDateTime));
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
	return checkIsYesterday(toLocalDate(localDateTime));
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
	return checkIsTommorow(toLocalDate(localDateTime));
    }

    public LocalDateTime getNowDateTime() {
	return LocalDateTime.now();
    }

    public LocalDate getNowDate() {
	return LocalDate.now();
    }
}
