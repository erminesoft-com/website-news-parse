package com.erminesoft.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.erminesoft.constants.TimeConstants.*;

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
        for (String pattern : patterns) {
            if (checkDatePattern(pattern, time)) return pattern;
        }
        return null;
    }

    private Date getDateFromString(String pubDay) {
        logger.info("getDateFromString() pubDay {}", pubDay);
        if (pubDay == null) return null;

        Date time = null;

        if (pubDay.contains(YESTERDAY)) {
            time = DateTime.now().minusDays(1).toDate();
            return time;
        }

        if (pubDay.contains(TODAY)) {
            time = new Date();
            return time;
        }

        if (pubDay.contains(HOURS) || pubDay.contains(HOUR)) {
            int number = getNumberOfString(pubDay);
            time = DateTime.now().minusHours(number).toDate();
            return time;
        }

        if (pubDay.contains(DAY) || pubDay.contains(DAYS)) {
            int number = getNumberOfString(pubDay);
            time = DateTime.now().minusDays(number).toDate();
            return time;
        }

        if (pubDay.contains(WEEK) || pubDay.contains(WEEKS)) {
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
