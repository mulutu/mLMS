/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author jmulutu
 */
public class LMSLogger {

    static Logger logger = Logger.getGlobal(); //   .getLogger(CheckCustomerRegistration.class.getName());

    public static void writeLog(String log) throws IOException {
        
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("cfg/logging/mylogging.properties"));
        } catch (IOException ex) {
            Logger.getLogger(LMSLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LMSLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        logger.setLevel(Level.FINE);
        logger.addHandler(new ConsoleHandler());
        logger.addHandler(new LMSLogHandler());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = formatter.format(new java.util.Date());

        String logName = "log/LMSServer_" + dateString + ".log";

        
            FileHandler fileHandler = new FileHandler(logName, true);
            fileHandler.setFormatter(new LMSLogFormatter());
            fileHandler.setFilter(new LMSLogFilter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.log(Level.INFO, log);
        
    }
}
