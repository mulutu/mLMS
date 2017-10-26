/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.cron;

import com.mpango.Util.FT.DoFT;
import com.mpango.Util.objects.FinancialTransaction;
import com.mpango.Util.objects.LoanApplicationTransaction;
import com.mpango.bus.LoanBUS;
import com.mpango.dao.lms.DBConnectionPool;
import com.mpango.dao.lms.DataProvider;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
public class ProcessLoanApplications extends QuartzJobBean {

    static Logger logger = Logger.getLogger(ProcessLoanApplications.class.getName());

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        processApplications();
    }

    public void processApplications() {
        List<LoanApplicationTransaction> loanApplicationsList = new ArrayList<>();
        Connection con = DataProvider.getConnection();
        loanApplicationsList = LoanBUS.getAllLoanApplications(con);
        for (LoanApplicationTransaction loanApplication : loanApplicationsList) {

            String debitAccount = "1111111111"; //loanApplication.getDebitAccount(); // THIS SHOULD BE THE LOAN CONTROL ACCOUNT
            String creditAccount = loanApplication.getCreditAccount();
            BigDecimal repaymentAmount = loanApplication.getAmount();
            String loanRef = loanApplication.getLoanRef();
            String trnTypeRef = loanApplication.getTransactionRef();

            FinancialTransaction FT = new FinancialTransaction();

            FT.setAmount(repaymentAmount);
            FT.setCreditAccount(creditAccount);
            FT.setDebitAccount(debitAccount);
            FT.setTransactionRefNum(loanRef);
            FT.setTransactionTypeID(1);  // 1 = LOAN APPLICATIONS
            FT.setStatus("ANY STATUS");
            FT.setTransactionTypeRef(trnTypeRef);

            int ftResult = DoFT.doLoanApplicationFT(FT, con);

            if (ftResult == 1) {

                int recordDisbursement = LoanBUS.recordDisbursement(loanApplication, con);
                if (recordDisbursement == 1) {
                    int output = LoanBUS.updateLoanApplicationStatus(loanApplication.getLoanRef(), "DISBURSED", con);
                    if (output == 1) {
                        int emi_schedule = LoanBUS.createEmiSchedule(loanApplication, con);
                        if (emi_schedule == 1) {
                            //
                        }
                    }
                }
            }
        }
        if (con != null) {
            DbUtils.closeQuietly(con);
        }
    }
}
