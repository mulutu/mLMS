/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.Util.log;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 *
 * @author jmulutu
 */
public class LMSLogFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return true;
    }
}
