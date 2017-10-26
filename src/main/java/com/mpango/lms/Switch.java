/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.lms;

import com.mpango.Util.Constant;
import com.mpango.Util.log.LMSLogISO;
import com.mpango.Util.MessageHelper;
import java.io.Serializable;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.GroupSelector;
import org.apache.log4j.Logger;

/**
 *
 * @author jmulutu
 */
public class Switch implements GroupSelector, Configurable {

    static Logger logger = Logger.getLogger(Switch.class);

    private Configuration cfg;

    @Override
    public void abort(long id, Serializable context) {
    }

    @Override
    public void commit(long id, Serializable context) {
    }

    @Override
    public int prepare(long id, Serializable context) {
        return PREPARED | READONLY | NO_JOIN;
    }

    @Override
    public String select(long id, Serializable context) {
        try {
            Context ctx = (Context) context;
            ISOMsg msg = (ISOMsg) ctx.get(Constant.REQUEST);
            String groups = "";
            String transactionName = MessageHelper.getTransactionType(msg);

            if (transactionName == null) {
                return cfg.get(Constant.ERROR_FLOW);
            }

            if (!Constant.BALANCE_INQUIRY_PROCESS.equals(transactionName)
                    && !Constant.LOAN_REPAYMENT.equals(transactionName)
                    && !Constant.LOAN_APPLICATION.equals(transactionName)
                    && !Constant.ACCOUNT_BALANCE_INQUIRY_PROCESS.equals(transactionName)) {
                return cfg.get(Constant.ERROR_FLOW);
            }

            if (msg.getMTI().equals(Constant.REVERSAL_MTI)) {
                groups = cfg.get(Constant.REVERSAL_PROCESS);
            } else {
                groups = cfg.get(transactionName);
            }

            logger.info("Processing code : " + transactionName);
            logger.info("Process : " + groups);
            logger.info(ctx.get(Constant.SESSION_ID) + " : " + "ReceivedISO [ " + LMSLogISO.ISOtoString(msg) + " ]");

            return groups;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setConfiguration(Configuration cfg)
            throws ConfigurationException {
        this.cfg = cfg;
    }
}
