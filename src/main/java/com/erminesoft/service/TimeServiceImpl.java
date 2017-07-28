package com.erminesoft.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TimeServiceImpl implements TimeService {
    private final static Logger logger = LoggerFactory.getLogger(TimeServiceImpl.class);

    private static final String MESSAGE_TITLE_ERROR = "Error parse web-site {}, {}";

    @Override
    public String getFinishTime(String date, String site) {
        logger.info("Entering getFinishTime() date = {},  site = {}", date, site);

        String result;
        String pattern = definePatten(date);

        if (pattern != null) {
            result = getDateFromStringAndPattern(date, pattern, site).toString();
            logger.info("Leaving getFinishTime() result = {}", result);
            return result;
        }

        Date prepareDate = getDateFromString(date);

        if (prepareDate != null) {
            logger.info("Leaving getFinishTime() prepareDate = {}", prepareDate.toString());
            return prepareDate.toString();
        }

        result = DateTime.now().minusDays(1).toDate().toString();
        logger.info("Leaving getFinishTime() with default time = {}", result);
        return result;
    }

    private Date getDateFromStringAndPattern(String time, String pattern, String site) {
        logger.info("Entering getDateFromStringAndPatern() time = {}, pattern = {}", time, pattern);
        if (time == null) return new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException i) {
            logger.warn(MESSAGE_TITLE_ERROR, site, i.getLocalizedMessage());
        } finally {
            if (date == null) {
                date = new Date();
            }
        }
        return date;
    }

    private String definePatten(String time) {
        List<String> patterns = Arrays.asList("dd/MM/yyyy", "E, dd MMM yyyy HH:mm:ss", "dd-mm-yyyy hh:mm a", "MMM dd, yyyy",
                "yyyy-MM-dd'T'HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss Z", "MM/dd/yy", "dd MMM, yyyy", "EEE, dd/mm/yyyy",
                "d MMMM yyyy | h:mm a", "dd.MM.yyyy", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd'T'HH:mm:ssXXX", "EEEE, dd/mm/yyyy");
        for (String pattern : patterns) {
            if (checkDatePattern(pattern, time)) return pattern;
        }
        return null;
    }

    private Date getDateFromString(String pubDay) {
        logger.info("getDateFromString() pubDay {}", pubDay);
        if (pubDay == null) return null;

        Date time = null;

        if (pubDay.contains("Yesterday")) {
            time = DateTime.now().minusDays(1).toDate();
            return time;
        }

        if (pubDay.contains("Today")) {
            time = new Date();
            return time;
        }

        if (pubDay.contains("hours") || pubDay.contains("hour")) {
            int number = getNumberOfString(pubDay);
            time = DateTime.now().minusHours(number).toDate();
            return time;
        }

        if (pubDay.contains("day") || pubDay.contains("days")) {
            int number = getNumberOfString(pubDay);
            time = DateTime.now().minusDays(number).toDate();
            return time;
        }

        if (pubDay.contains("week") || pubDay.contains("weeks")) {
            int number = getNumberOfString(pubDay);
            time = DateTime.now().minusWeeks(number).toDate();
            return time;
        }
        logger.info("getDateFromString() pubDay {}", pubDay);
        return time;
    }

    private boolean checkDatePattern(String pattern, String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            format.parse(date);
            logger.info("Leaving checkDatePattern() with result - true, pattern = {}, time = {}", pattern, date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int getNumberOfString(String text) {
        int res = 0;
        String result;
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            result = matcher.group();
            res = Integer.valueOf(result);
        }
        return res;
    }
}
