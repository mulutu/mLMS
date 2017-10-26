/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util;

/**
 *
 * @author jmulutu
 */
public class WorkerDueLoans implements Runnable {

    private final Object lock1 = new Object();

    @Override
    public void run() {
        process();
    }

    public void process() {
        //disburseLoan();
        //checkDueLoans();
    }

}
