/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.lms;

import com.mpango.Util.Constant;
import com.mpango.Util.MessageHelper;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.transaction.Context;
import org.jpos.util.ThreadPool;

/**
 *
 * @author jmulutu
 */
public class ProcessIncomingRequests implements Runnable {
    
    static Logger logger = Logger.getLogger(ProcessIncomingRequests.class);

    private String name = null;

    private String queueName;
    private Space<String, Context> sp;
    private Long timeout;
    ThreadPool pool;
    ISOSource isoSrc;
    ISOMsg isoMsg;
    Context ctx;

    /*public ProcessIncomingRequests(String name) {
     this.name = name;
     }*/
    public ProcessIncomingRequests(ISOSource source, ISOMsg isoMsg, String queueName, Long timeout, Space<String, Context> sp, Context ctx) {
        //super();
        this.isoSrc = source;
        this.isoMsg = isoMsg;
        this.queueName = queueName;
        this.timeout = timeout;
        this.sp = sp;
        this.ctx = ctx;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void run() {
        processRequests();
    }

    public void processRequests() {
        String trxRefNumber = MessageHelper.getTransactionRef(isoMsg);
        
        Context ctx = new Context();
        ctx.put(Constant.REQUEST, isoMsg);
        ctx.put(Constant.SOURCE, isoSrc);
        ctx.put(Constant.SESSION_ID, trxRefNumber );
        sp.out(queueName, ctx, timeout);
        
        // receivedISO = new String(isoMsg.pack());
    }
}