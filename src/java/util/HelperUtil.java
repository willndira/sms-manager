/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Norrey Osako
 */
public class HelperUtil implements Serializable {

    public static Date getTodaysDate() {
        Date date = new Date();
        return date;
    }

    public static Date setEndDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String endDateInString = "2099-12-31 00:00:00";
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(endDateInString);
        } catch (ParseException ex) {
            Logger.getLogger(HelperUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return endDate;
    }

    public static String convertPhonenumber(String phone) {

        if (!phone.isEmpty()) {
            if (phone != null) {
                if (phone.charAt(0) == '0') {
                    phone = phone.replaceFirst("07", "2547");
                }

            }
        }
        return phone;
    }

    public static String conDateToString(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        if (d != null) {
            return df.format(d);
        } else {
            return df.format(new Date());
        }

    }

    public static boolean checkEmail(String email) {
        String EMAIL_PATTERN
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Date convetStringToDate(String d) {
        if (stringCharcters(d)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                d = d.trim();
                return (df.parse(d));
            } catch (ParseException ex) {
                Logger.getLogger(HelperUtil.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return new java.util.Date();
        }

    }

    public static java.sql.Date conUtilDateToSqlDate(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String da = df.format(d);
        Date d1 = convetStringToDate(da);
        java.util.Date utilDate = d1;
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    public static java.sql.Date conUtilDateToTimestamp(Date d) {
        java.sql.Date sqlDate = new java.sql.Date(d.getTime());
        return sqlDate;
    }

    public static boolean stringCharcters(String value) {
        if (value != null) {
            return value.trim().length() > 0;
        } else {
            return false;
        }

    }

}
