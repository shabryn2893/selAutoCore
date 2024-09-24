package io.github.shabryn2893.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

/**
 * Utility class for managing date operations.
 * Provides methods for date calculations, formatting, and retrieving specific date information.
 *
 * Example usage:
 * 
 * String nextFriday = DateManager.getDateBasedOnDayNameForCurrentWeek("Friday", "yyyy-MM-dd");
 * System.out.println("Next Friday: " + nextFriday);
 */
public class DateManager {

	 private static final Logger logger = LoggerUtils.getLogger(DateManager.class);
    
    // Private constructor to prevent instantiation
    private DateManager() {
    	throw new UnsupportedOperationException("DateManager class should not be instantiated");
    }

    /**
     * Gets a Calendar object representing a date that is a specified number of days in the future.
     *
     * @param daysOut the number of days to add to the current date.
     * @return a Calendar object set to the future date.
     */
    private static Calendar getDateAfterDaysOut(Integer daysOut) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysOut);
        return calendar;
    }

    /**
     * Returns a formatted date string for a date that is a specified number of days in the future.
     *
     * @param daysOut      the number of days to add to the current date.
     * @param desiredFormat the desired date format (e.g., "yyyy-MM-dd").
     * @return the formatted date string.
     */
    public static String getDaysOut(Integer daysOut, String desiredFormat) {
        return formatDate(getDateAfterDaysOut(daysOut), desiredFormat);
    }

    /**
     * Returns a formatted date string for a date that is a specified number of months in the future.
     *
     * @param monthsOut    the number of months to add to the current date.
     * @param desiredFormat the desired date format (e.g., "yyyy-MM-dd").
     * @return the formatted date string.
     */
    public static String getMonthByMonthsOut(Integer monthsOut, String desiredFormat) {
        Calendar currentDate = getDateAfterDaysOut(0);
        currentDate.add(Calendar.MONTH, monthsOut);
        return formatDate(currentDate, desiredFormat);
    }

    /**
     * Gets the start and end dates of a week that is a specified number of weeks in the future.
     *
     * @param weeksOut    the number of weeks to add to the current date.
     * @param format      the desired date format (e.g., "yyyy-MM-dd").
     * @return a string representing the start and end dates of the week.
     */
    public static String getStartEndWeekDate(Integer weeksOut, String format) {
        Calendar currentDate = getDateAfterDaysOut(0);
        currentDate.add(Calendar.WEEK_OF_MONTH, weeksOut);
        
        String startDate = formatDate(currentDate, format, Calendar.SUNDAY);
        String endDate = formatDate(currentDate, format, Calendar.SATURDAY);
        
        return startDate + " – " + endDate;
    }

    /**
     * Returns the name of the day for a given date string.
     *
     * @param dayDate a string representing the date.
     * @param format  the format of the input date string.
     * @return the name of the day (e.g., "Monday") or an empty string if an error occurs.
     */
    public static String getDayNameForDate(String dayDate, String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(dayDate);
            return new SimpleDateFormat("EEEE").format(date);
        } catch (ParseException e) {
            logger.error("Error parsing date: {} {}",dayDate, e);
            return ""; // Return empty string if error occurs
        }
    }

    /**
     * Gets the first and last date of the current month in the specified format.
     *
     * @param dateFormat the desired date format (e.g., "yyyy-MM-dd").
     * @return a string representing the first and last date of the current month.
     */
    public static String getFirstAndLastDateForCurrentMonth(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        String firstDay = formatDate(cal, dateFormat);
        
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = formatDate(cal, dateFormat);
        
        return firstDay + "-" + lastDay;
    }

    /**
     * Gets the date for a specified day of the week in the next occurrence.
     *
     * @param dayName the name of the day (e.g., "Monday").
     * @param format   the desired date format (e.g., "yyyy-MM-dd").
     * @return the formatted date string of the next occurrence of the specified day.
     */
    public static String getDateBasedOnDayNameForCurrentWeek(String dayName, String format) {
        DayOfWeek day = DayOfWeek.valueOf(dayName.toUpperCase());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(LocalDate.now().with(TemporalAdjusters.next(day)));
    }

    /**
     * Gets all date strings between a range of days relative to the current date.
     *
     * @param startDaysOut the starting days relative to today.
     * @param endDaysOut   the ending days relative to today.
     * @param dateFormat   the desired date format (e.g., "yyyy-MM-dd").
     * @return a list of formatted date strings.
     */
    public static List<String> getAllDateBetweenDateRange(int startDaysOut, int endDaysOut, String dateFormat) {
        List<String> dates = new ArrayList<>();
        for (int i = startDaysOut; i <= endDaysOut; i++) {
            dates.add(getDaysOut(i, dateFormat));
        }
        return dates;
    }

    /**
     * Gets the current timestamp formatted in the specified way.
     *
     * @param format the desired format (e.g., "yyyy-MM-dd HH:mm:ss").
     * @return the formatted current timestamp string.
     */
    public static String getTimestamp(String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Calculates and returns the year, month, and date for a specified number of days in the future.
     *
     * @param daysOut the number of days to add to the current date.
     * @return a map containing the year, month, and date.
     */
    public static Map<String, String> calculateDaysOut(Integer daysOut) {
        Map<String, String> finalDate = new HashMap<>();
        Calendar calendar = getDateAfterDaysOut(daysOut);
        finalDate.put("YEAR", Integer.toString(calendar.get(Calendar.YEAR)));
        finalDate.put("MONTH", new SimpleDateFormat("MMM").format(calendar.getTime()).toUpperCase());
        finalDate.put("DATE", Integer.toString(calendar.get(Calendar.DATE)));
        return finalDate;
    }

    /**
     * Helper method to format a Calendar object into a string based on the specified format.
     *
     * @param calendar the Calendar object to format.
     * @param format   the desired date format (e.g., "yyyy-MM-dd").
     * @return the formatted date string.
     */
    private static String formatDate(Calendar calendar, String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    /**
     * Overloaded helper method to format a Calendar object for a specific day of the week.
     *
     * @param calendar the Calendar object to format.
     * @param format   the desired date format (e.g., "yyyy-MM-dd").
     * @param dayOfWeek the day of the week to set in the Calendar.
     * @return the formatted date string.
     */
    private static String formatDate(Calendar calendar, String format, int dayOfWeek) {
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return formatDate(calendar, format);
    }
}
