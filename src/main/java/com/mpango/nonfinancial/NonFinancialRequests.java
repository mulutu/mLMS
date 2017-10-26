/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.nonfinancial;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import org.apache.log4j.Logger;

/**
 *
 * @author jmulutu
 */
public class NonFinancialRequests {
    
    static Logger logger = Logger.getLogger(NonFinancialRequests.class.getName());
    
    Connection con = null;

    ServerSocket s;

    public void go() {
        try {
            s = new ServerSocket(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket incoming;
            try {
                incoming = s.accept();
                Thread t = new Thread(new ChannelCon(incoming, s ));
                t.start();
            } catch (IOException ex) {
                logger.fatal(ex);
            }
        }
    }
    /* public static void main(String[] args) {
        new NonFinancialRequests().go();
    } */
}
