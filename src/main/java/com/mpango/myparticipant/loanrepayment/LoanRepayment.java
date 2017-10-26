/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.myparticipant.loanrepayment;

import org.apache.log4j.Logger;
import com.mpango.Util.Constant;
import com.mpango.Util.MessageHelper;
import com.mpango.bus.LoanBUS;
import com.mpango.bus.LoanDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import static org.jpos.transaction.TransactionConstants.ABORTED;
import static org.jpos.transaction.TransactionConstants.NO_JOIN;
import static org.jpos.transaction.TransactionConstants.PREPARED;
import static org.jpos.transaction.TransactionConstants.READONLY;
import org.jpos.transaction.TransactionParticipant;

/**
 *
 * @author jmulutu
 */
public class LoanRepayment implements TransactionParticipant {

    static Logger logger = Logger.getLogger(LoanRepayment.class);

    @Override
    public void abort(long arg0, Serializable arg1) {
    }

    @Override
    public void commit(long arg0, Serializable arg1) {
    }

    @Override
    public int prepare(long id, Serializable serializable) {

        Context ctx = (Context) serializable;
        ISOMsg msg = (ISOMsg) ctx.get(Constant.REQUEST);
        Connection con = (Connection) ctx.get(Constant.CONN);

        if (con == null) {
            ctx.put(Constant.RC, "12");
            return ABORTED | READONLY | NO_JOIN;
        }
        if (msg != null) {

            /* Get transaction details: */
            BigDecimal repayAmount = MessageHelper.getRepaymentAmount(msg);
            String loanRefNumber = MessageHelper.getLoanRefNumber(msg);
            String transactionRef = MessageHelper.getTransactionRef(msg);
            String debitAccount = MessageHelper.getDebitAccount(msg);
            String creditAccount = MessageHelper.getCreditAccount(msg);

            logger.info(ctx.get(Constant.SESSION_ID) + " : Loan number " + loanRefNumber + "");
            logger.info(ctx.get(Constant.SESSION_ID) + " : Debit Account " + debitAccount + "");
            logger.info(ctx.get(Constant.SESSION_ID) + " : Credit Account " + creditAccount + "");       
            

            /* If repayAmount is null */
            if (repayAmount == null || repayAmount.compareTo(BigDecimal.ZERO) == 0) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : Repay amount: NULL ");
                return ABORTED | READONLY | NO_JOIN;
            }

            /* Get loanDTO - Loan Data Object - loan details */
            LoanDTO loanDetails = LoanBUS.getLoanByRef(loanRefNumber, con);
            loanDetails.setControlAccount(creditAccount);
            loanDetails.setRepaymentAccount(debitAccount);
            
            logger.info(ctx.get(Constant.SESSION_ID) + " : Get loan details ");

            /* Get the loan balance */
            BigDecimal loanBalance = loanDetails.getLoanBalance();

            /* If loanBalance is ZERO */
            if (loanBalance.compareTo(BigDecimal.ZERO) == 0) {
                ctx.put(Constant.RC, "19");
                logger.info(ctx.get(Constant.SESSION_ID) + " : Loan already cleared ");
                return ABORTED | READONLY | NO_JOIN;
            }

            int result = LoanBUS.recordRepayment(loanDetails, repayAmount, transactionRef, con);
            logger.info(ctx.get(Constant.SESSION_ID) + " : Record loan repayment ");

            if (result == -1) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : Error occurred ");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (result == 0 || result == 2 || result == 3) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : Error occurred ");
                return ABORTED | READONLY | NO_JOIN;
            }
            logger.info(ctx.get(Constant.SESSION_ID) + " : Current loan balance: " + loanBalance);
            logger.info(ctx.get(Constant.SESSION_ID) + " : Repay amount: " + repayAmount);

            /* Loan status is OK. */
            return PREPARED | READONLY | NO_JOIN;

        } else {
            ctx.put(Constant.RC, "12");
            logger.info(ctx.get(Constant.SESSION_ID) + " :  ISO message is null ");
            return ABORTED | NO_JOIN;
        }
    }
}
