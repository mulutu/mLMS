/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util;

import com.mpango.bus.LoanBUS;
import com.mpango.dao.lms.DataProvider;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.transaction.Context;
import org.jpos.util.NameRegistrar;

/**
 *
 * @author jmulutu
 */
public class Worker implements Runnable {

    private Random random = new Random();
    Connection con = DataProvider.getConnection();
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    @Override
    public void run() {
        process();
    }

    public void process() {

        if (con != null) {
            updateEMISchedule(con);
        }
        disburseLoan();

    }

    public void disburseLoan() {
        synchronized (lock1) {
            System.out.println("disburseLoan: ");
            //UtilityFunctions.sendToMpesa(, null);
        }
    }

    public void updateEMISchedule(Connection con) {
        synchronized (lock2) {

            LoanBUS.updateEMISchedule(con);

            /*try {
             con.close();
             } catch (SQLException ex) {
             Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            System.out.println("updateEMISchedule: ");
        }
    }
}
