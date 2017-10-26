package com.mpango.myparticipant.loanbalanceinquiry;

import com.mpango.Util.Constant;
import com.mpango.Util.MessageHelper;
import com.mpango.bus.LoanBUS;
import com.mpango.bus.LoanDTO;
import java.io.Serializable;
import java.sql.Connection;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import static org.jpos.transaction.TransactionConstants.ABORTED;
import static org.jpos.transaction.TransactionConstants.NO_JOIN;
import static org.jpos.transaction.TransactionConstants.READONLY;
import org.jpos.transaction.TransactionParticipant;

/**
 * Get point information of client.
 *
 * @author HUNGPT
 *
 */
public class ValidateLoan implements TransactionParticipant {

    static Logger logger = Logger.getLogger(ValidateLoan.class);

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

            String loanRefNumber = MessageHelper.getLoanRefNumber(msg);

            logger.info(ctx.get(Constant.SESSION_ID) + " : " + loanRefNumber + " Validate loan");

            LoanDTO loandetails = LoanBUS.getLoanByRef(loanRefNumber, con);

            if (loandetails == null) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + loanRefNumber + " Error occurred");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (loandetails.getLoanRef() == "") {
                ctx.put(Constant.RC, "19"); // an error occured
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + loanRefNumber + " Loan does not exist!");
                return ABORTED | READONLY | NO_JOIN;
            }

            ctx.put(Constant.LOAN_DUE_DATE, loandetails.getDueDate());
            ctx.put(Constant.LOAN_BALANCE, loandetails.getLoanBalance());
            return PREPARED | READONLY | NO_JOIN;

        } else {
            ctx.put(Constant.RC, "12");
            logger.info(ctx.get(Constant.SESSION_ID) + " : Error occurred!");
            return ABORTED | NO_JOIN;
        }
    }

}
