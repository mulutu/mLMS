/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.bus;

import com.mpango.Util.objects.LoanApplicationTransaction;
import com.mpango.Util.objects.LoanRepaymentTransaction;
import com.mpango.dao.factory.LMSDAOFactory;
import com.mpango.dao.idao.I_Loan;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author jmulutu
 */
public class LoanBUS {
    
    public static int LoanRepaymentSP(String loanRef, String trxRef, BigDecimal repayAmount, String FTNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.LoanRepaymentSP(loanRef, trxRef, repayAmount, FTNumber, con);
    }
    
    public static  int updateRepayment(LoanRepaymentTransaction loanrepayment, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.updateRepayment(loanrepayment, con);
    }
    
    public static List<LoanRepaymentTransaction>  getAllLoanRepayments(Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getAllLoanRepayments(con);
    }

    public static List<LoanApplicationTransaction> getAllLoanApplications(Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getAllLoanApplications(con);
    }

    public static void updateEMISchedule(Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        myLoan.updateEMISchedule(con);
    }

    public static List<String> getDueLoans(int dueInDays, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getDueLoans(dueInDays, con);
    }

    public static int updateEMIPayment(LoanRepaymentTransaction loanrepayment, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.updateEMIPayment(loanrepayment, con);
    }

    public static int getInstallmentNo(String loanRef, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getInstallmentNo(loanRef, con);
    }

    public static LoanDTO getLoanByID(int loanID, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanByID(loanID, con);
    }

    public static LoanDTO getLoanByRef(String loanRef, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanByRef(loanRef, con);
    }

    public static int loanApplication(LoanApplicationTransaction loanapplication, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.loanApplication(loanapplication, con);
    }

    public static LoanDTO getLoanApplicationByLoanRef(String loanRef, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanApplicationByLoanRef(loanRef, con);
    }

    public static BigDecimal getLoanBalance(String loanRefNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanBalance(loanRefNumber, con);
    }

    public static BigDecimal getLoanTotalRepayments(String loanRefNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanTotalRepayments(loanRefNumber, con);
    }

    public static int LoanRepayment(LoanRepaymentTransaction loanrepayment, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.LoanRepayment(loanrepayment,con);
    }

    public static BigDecimal getInterestRate(String loanRefNumber, int repaymentPeriod, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getInterestRate(loanRefNumber, repaymentPeriod, con);
    }

    public static BigDecimal getInterestAmount(BigDecimal interestRate, BigDecimal loanAmount) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getInterestAmount(interestRate, loanAmount);
    }

    public static int recordDisbursement(LoanApplicationTransaction loanApplication, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.recordDisbursement(loanApplication, con);
    }

    public static int updateLoanApplicationStatus(String loanRefNumber, String status, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.updateLoanApplicationStatus(loanRefNumber, status, con);
    }

    public static int recordRepayment(LoanDTO loanDetails, BigDecimal repayAmount, String transactionRef, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.recordRepayment(loanDetails, repayAmount,transactionRef, con);
    }

    public static BigDecimal calculateEMI(LoanApplicationTransaction loanApplication, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.calculateEMI(loanApplication, con);
    }

    public static int createEmiSchedule(LoanApplicationTransaction loanApplication, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.createEmiSchedule(loanApplication, con);
    }
}
