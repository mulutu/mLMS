package com.mpango.myparticipant.accountbalance;

import com.mpango.Util.Constant;
import com.mpango.Util.MessageHelper;
import com.mpango.Util.objects.Account;
import com.mpango.bus.AccountBUS;
import com.mpango.bus.LoanBUS;
import com.mpango.bus.LoanDTO;
import com.mpango.myparticipant.loanbalanceinquiry.*;
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
public class ValidateAccount implements TransactionParticipant {

    static Logger logger = Logger.getLogger(ValidateAccount.class);

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

            String FOSAAccountNumber = MessageHelper.getFOSAAccountNumber(msg);

            logger.info(ctx.get(Constant.SESSION_ID) + " : " + FOSAAccountNumber + " Validate FOSA account");

            Account accountDetails = AccountBUS.getAccount(FOSAAccountNumber, con);

            if (accountDetails == null) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + FOSAAccountNumber + " Error occurred");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (accountDetails.getAccountNumber()== "") {
                ctx.put(Constant.RC, "19"); // an error occured
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + FOSAAccountNumber + " Account does not exist!");
                return ABORTED | READONLY | NO_JOIN;
            }
            
            if (accountDetails.getActive()== 0) {
                ctx.put(Constant.RC, "18"); // an error occured
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + FOSAAccountNumber + " Account is inactive!");
                return ABORTED | READONLY | NO_JOIN;
            }

            ctx.put(Constant.ACCOUNT_BALANCE, accountDetails.getAccountBalance().toPlainString());
            return PREPARED | READONLY | NO_JOIN;

        } else {
            ctx.put(Constant.RC, "12");
            logger.info(ctx.get(Constant.SESSION_ID) + " : Error occurred!");
            return ABORTED | NO_JOIN;
        }
    }

}
