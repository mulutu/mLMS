/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.myparticipant;

import com.mpango.Util.Worker;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jpos.transaction.TransactionParticipant;

/**
 *
 * @author jmulutu
 */
public class LoanManagerWorker implements TransactionParticipant {

    public void startThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(20); //two threads, try setting by 1 to observe time
        Worker worker = new Worker();

        boolean status = true;

        while (status) {
            executor.submit(worker);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public int prepare(long l, Serializable srlzbl) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        startThreads();

        return PREPARED | READONLY | NO_JOIN;
    }

    @Override
    public void commit(long l, Serializable srlzbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void abort(long l, Serializable srlzbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
