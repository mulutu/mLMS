/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author jmulutu
 */
public class LMSLogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS");
        String dateString = formatter.format(new java.util.Date());

        //String log = "[ " + new Date(record.getMillis()) + " ] "
        String log = "[ " + dateString + " ] "
                + "[" + record.getLevel() + " ] "
                + record.getMessage() + "\n";

        return log;
    }
}
