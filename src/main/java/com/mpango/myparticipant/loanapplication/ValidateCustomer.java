/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.import com.mpango.Util.Constant;

 */
package com.mpango.myparticipant.loanapplication;

import com.mpango.Util.CheckCRB;
import com.mpango.Util.Constant;
import com.mpango.Util.IdGenerator;
import com.mpango.Util.MessageHelper;
import com.mpango.Util.objects.LoanApplicationTransaction;
import com.mpango.bus.CustomerBUS;
import com.mpango.bus.CustomerDTO;
import com.mpango.bus.LoanBUS;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import org.apache.log4j.Logger;
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
public class ValidateCustomer implements TransactionParticipant {

    static Logger logger = Logger.getLogger(ValidateCustomer.class);

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
        IdGenerator randomGen = new IdGenerator();

        if (con == null) {
            ctx.put(Constant.RC, "12");
            return ABORTED | READONLY | NO_JOIN;
        }

        if (msg != null) {
            String loanRef = "LN" + randomGen.generateId(5);
            String CustomerMSISDN = MessageHelper.getCustomerMSISDN(msg);
            BigDecimal loanAmount = MessageHelper.getAmountApplied(msg);
            String transactionRef = MessageHelper.getTransactionRef(msg);
            int RepaymentPeriod = MessageHelper.getLoanRepaymentPeriod(msg);
            int LoanTypeID = MessageHelper.getLoanTypeID(msg);

            /* Get loanDTO - Loan Data Object - loan details */
            CustomerDTO customer = CustomerBUS.getCustomerByMSISDN(CustomerMSISDN, con);

            /* Check if customer is registered */
            if (customer.getID_NUMBER().isEmpty()) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Error: Customer is not registered");
                return ABORTED | READONLY | NO_JOIN;
            }

            /* Check CRB status of teh customer  */
            int crbstatus = CheckCRB.getCustomerCRBStatus();

            if (crbstatus == -1) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "CRB Error");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (crbstatus == 1) {
                ctx.put(Constant.RC, "14");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Customer is CRB listed");
                return ABORTED | READONLY | NO_JOIN;
            }
            logger.info(ctx.get(Constant.SESSION_ID) + " : " + "CRB Status OK");

            ctx.put(Constant.CRB_STATUS, crbstatus);

            /* Check if customer has an existing loan  */
            int hasExistingLoanApplication = CustomerBUS.hasExistingLoanApplication(customer.getID_NUMBER(), con);
            if (hasExistingLoanApplication == -1) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Customer has existing loan application");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (hasExistingLoanApplication == 1) {
                ctx.put(Constant.RC, "50");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Customer has existing loan application");
                return ABORTED | READONLY | NO_JOIN;
            }

            /* Check if customer has an existing loan */
            int hasExistingLoan = CustomerBUS.hasExistingLoan(customer.getID_NUMBER(), con);
            if (hasExistingLoan == -1) {
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Customer has existing loan");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (hasExistingLoan == 1) {
                ctx.put(Constant.RC, "16");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Customer has existing loan");
                return ABORTED | READONLY | NO_JOIN;
            }

            /* Cheeck customer limit */
            logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Check customer loan limits");

            BigDecimal loanLimit = customer.getLoanLimit();

            int res = loanLimit.compareTo(new BigDecimal("0", MathContext.DECIMAL64));

            if (res == 0 || res == -1) { //System.out.println("Both values are equal ");
                ctx.put(Constant.RC, "12");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Customer has has no loan limit set");
                return ABORTED | READONLY | NO_JOIN;
            }

            if (loanLimit.compareTo(loanAmount) < 0) {
                ctx.put(Constant.RC, "51");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + " Customer has a lower loan limit [" + loanLimit + "] vs loan request [" + loanAmount + "]");
                return ABORTED | READONLY | NO_JOIN;
            }

            /* Record the loan application */
            BigDecimal interestRate = null;
            String interestRateType = "";
            
            LoanApplicationTransaction loanapplication = new LoanApplicationTransaction();
            loanapplication.setCustomer(customer);
            loanapplication.setTransactionRef(transactionRef);
            loanapplication.setLoanRef(loanRef);
            loanapplication.setLoanTypeID(LoanTypeID);
            loanapplication.setAmount(loanAmount);
            loanapplication.setRepaymentDuration(RepaymentPeriod);
            loanapplication.setInterestRate(interestRate);
            loanapplication.setInterestRateType(interestRateType);
            
            int resultLA = LoanBUS.loanApplication(loanapplication, con);

            if (resultLA == 0) {
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "Loan application failed");
                return ABORTED | READONLY | NO_JOIN;
            }
            ctx.put(Constant.LOAN_REF_NUMBER, loanRef);
            return PREPARED | READONLY | NO_JOIN;

        } else {
            ctx.put(Constant.RC, "12");
            return ABORTED | NO_JOIN;
        }
    }
}
