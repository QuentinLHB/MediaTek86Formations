package com.example.mediatek86formations.outils;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MesOutilsTest {

    @Test
    public void convertStringToDate() {
        String dateString = "2020-12-28 22:00:29";
        String pattern = "yyyy-MM-dd hh:mm:ss";
        Calendar calendar = new GregorianCalendar(2020,Calendar.DECEMBER,28,22,0,29);
        Date expected = calendar.getTime();
        Date actual = MesOutils.convertStringToDate(dateString, pattern);
        Assert.assertEquals(0, actual.compareTo(expected));
    }

    @Test
    public void testConvertStringToDate() {
        String dateString = "lun. mars 21 08:59:13 GMT+00:00 2022";
        Calendar calendar = new GregorianCalendar(2022,Calendar.MARCH,21,8,59,13);
        Date expected = calendar.getTime();
        Date actual = MesOutils.convertStringToDate(dateString);
        Assert.assertEquals(0, actual.compareTo(expected));
    }

    @Test
    public void convertDateToString() {
        Calendar calendar = new GregorianCalendar(2022,Calendar.MARCH,21,8,59,13);
        Date actual = calendar.getTime();
        Assert.assertEquals("21/03/2022", MesOutils.convertDateToString(actual));
    }
}