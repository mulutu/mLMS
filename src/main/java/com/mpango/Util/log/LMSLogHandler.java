/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.log;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 *
 * @author jmulutu
 */
public class LMSLogHandler extends StreamHandler {

    @Override
    public void publish(LogRecord record) {
        //add own logic to publish
        super.publish(record);
    }

    @Override
    public void flush() {
        super.flush();
    }

    @Override
    public void close() throws SecurityException {
        super.close();
    }
}
