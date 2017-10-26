/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.cron;

import com.mpango.Util.FT.DoFT;
import com.mpango.Util.IdGenerator;
import com.mpango.Util.objects.FinancialTransaction;
import com.mpango.Util.objects.LoanRepaymentTransaction;
import com.mpango.bus.LoanBUS;
import com.mpango.dao.lms.DBConnectionPool;
import com.mpango.dao.lms.DataProvider;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author jmulutu
 */
public class ProcessLoanRepayments extends QuartzJobBean {

    static Logger logger = Logger.getLogger(ProcessLoanRepayments.class.getName());
    private static IdGenerator randomGen = new IdGenerator();

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        processRepayments();
    }

    public void processRepayments() {

        List<LoanRepaymentTransaction> LoanRepaymentList = new ArrayList<>();
        Connection con = DataProvider.getConnection();
        LoanRepaymentList = LoanBUS.getAllLoanRepayments(con);

        for (LoanRepaymentTransaction loanrepayment : LoanRepaymentList) {
            
            System.out.println("loan repaynents: loanrepayment.getLoanRef()-->" + loanrepayment.getLoanRef()); 
            System.out.println("loan repaynents: loanrepayment.getTransactionRefNum()-->" + loanrepayment.getTransactionRefNum()); 
            
            String FTNumber = "FT" + randomGen.generateId(5);
            
            int repaymentStatus2 = LoanBUS.LoanRepaymentSP(
                    loanrepayment.getLoanRef(), 
                    loanrepayment.getTransactionRefNum(), 
                    loanrepayment.getRepaymentAmount(), 
                    FTNumber,
                    con
            );
            
            System.out.println("repaymentStatus2 --> " + repaymentStatus2);
            
            /*String debitAccount = loanrepayment.getDebitAccount();
            String creditAccount = loanrepayment.getCreditAccount();
            BigDecimal repaymentAmount = loanrepayment.getRepaymentAmount();
            String loanRef = loanrepayment.getLoanRef();            
            String trnTypeRef = loanrepayment.getTransactionRefNum();
            BigDecimal interestRate = loanrepayment.getInterestRate();
            
            System.out.println("interestRate " + interestRate);
            
            FinancialTransaction FT =  new FinancialTransaction();
            
            FT.setAmount(repaymentAmount);
            FT.setCreditAccount(creditAccount);
            FT.setDebitAccount(debitAccount);
            FT.setTransactionRefNum(loanRef);
            FT.setTransactionTypeID(2);  // 2 = LOAN REPAYMENT
            FT.setStatus("ANY STATUS");
            FT.setTransactionTypeRef(trnTypeRef);
            FT.setInterestRate(interestRate);            

            int ftResult = DoFT.doLoanRepaymentFT(FT, con);

            if (ftResult == 1) {

                int repaymentStatus = LoanBUS.LoanRepayment(loanrepayment, con);

                if (repaymentStatus == 1) {
                    int emiResult = LoanBUS.updateEMIPayment(loanrepayment, con);
                    if (emiResult == 1) {
                        // update repayment
                        int repaymentUpdateStatus = LoanBUS.updateRepayment(loanrepayment, con);
                        if (repaymentUpdateStatus == 1) {
                            // UPDATE DSBURSEMENTS TO CLOSE THE LOAN
                        }
                    }
                }
            } */
        }
        if (con != null) {
            DbUtils.closeQuietly(con);
        }
    }
}
