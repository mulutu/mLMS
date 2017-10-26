/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.idao;

import com.mpango.Util.objects.LoanApplicationTransaction;
import com.mpango.Util.objects.LoanRepaymentTransaction;
import com.mpango.bus.LoanDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author jmulutu
 */
public interface I_Loan {
    
    public int LoanRepaymentSP(String loanRef, String trxRef, BigDecimal repayAmount, String FTNumber, Connection con);
    
    public int updateRepayment(LoanRepaymentTransaction loanrepayment, Connection con);
    
    public List<LoanRepaymentTransaction> getAllLoanRepayments( Connection con);
    
    public List<LoanApplicationTransaction> getAllLoanApplications( Connection con);

    public void updateEMISchedule(Connection con);

    public List<String> getDueLoans(int dueInDays, Connection con);

    public int updateEMIPayment(LoanRepaymentTransaction loanrepayment, Connection con);

    public int getInstallmentNo(String loanRef, Connection con);

    LoanDTO getLoanByID(int loanID, Connection con);

    LoanDTO getLoanByRef(String loanRef, Connection con);

    public int loanApplication(LoanApplicationTransaction loanapplication, Connection con);

    LoanDTO getLoanApplicationByLoanRef(String loanRef, Connection con);

    public BigDecimal getLoanBalance(String loanRefNumber, Connection con);

    public BigDecimal getLoanTotalRepayments(String loanRefNumber, Connection con);
    
    public int LoanRepayment(LoanRepaymentTransaction loanrepayment, Connection con);

    public BigDecimal getInterestRate(String loanRefNumber, int repaymentPeriod, Connection con);

    public BigDecimal getInterestAmount(BigDecimal interestRate, BigDecimal loanAmount);

    public int recordDisbursement(LoanApplicationTransaction loanApplication, Connection con);

    public int updateLoanApplicationStatus(String loanRefNumber, String status, Connection con);

    public int recordRepayment(LoanDTO loanDetails, BigDecimal repayAmount, String transactionRef, Connection con);

    public BigDecimal calculateEMI(LoanApplicationTransaction loanApplication, Connection con);

    public int createEmiSchedule(LoanApplicationTransaction loanApplication, Connection con);
}
