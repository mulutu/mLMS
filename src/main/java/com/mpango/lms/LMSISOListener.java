/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.lms;

import com.mpango.nonfinancial.NonFinancialRequests;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.q2.Q2;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.transaction.Context;
import org.jpos.util.ThreadPool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jmulutu
 */
public class LMSISOListener implements ISORequestListener, Configurable {

    static Logger logger = Logger.getLogger(LMSISOListener.class.getName());

    //private static int portNumber;
    private String queueName;
    private Space<String, Context> sp;
    private Long timeout;

    ThreadPool pool = null;

    public LMSISOListener() {
        super();
    }

    public static void startQ2() {
        Q2 q2 = new Q2();
        q2.start();
    }

    public static void main(String[] args) throws Exception {
        startQ2();
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
        new NonFinancialRequests().go();
        BasicConfigurator.configure();
    }

    @Override
    public boolean process(ISOSource isoSrc, ISOMsg isoMsg) {
        Context ctx = new Context();
        workerThreads(isoSrc, isoMsg, queueName, timeout, sp, ctx);
        return true;
    }

    public void workerThreads(ISOSource isoSrc, ISOMsg isoMsg, String queueName, Long timeout, Space<String, Context> sp, Context ctx) {
        List<Runnable> queue = new ArrayList<Runnable>();

        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<Runnable>(50);
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 50, 5000, TimeUnit.MILLISECONDS, blockingQueue);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //executor.execute(r);
                if (!executor.isShutdown()) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        ;
                    }
                }
            }
        });
        executor.prestartAllCoreThreads();

        Runnable processIncoming = new ProcessIncomingRequests(isoSrc, isoMsg, queueName, timeout, sp, ctx);
        //Runnable processLoanApplications = new ProcessLoanApplications(isoSrc, isoMsg, queueName, timeout, sp, ctx);

        queue.add(processIncoming);
        //queue.add(processLoanApplications);

        for (int i = 0; i < queue.size(); i++) {
            executor.execute(queue.get(i));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        queueName = cfg.get("queue");
        timeout = cfg.getLong("timeout");
        sp = SpaceFactory.getSpace(cfg.get("space"));
    }
}
