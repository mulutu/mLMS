/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.lms;

import com.mpango.Util.FT.DoFT;
import com.mpango.Util.objects.LoanApplicationTransaction;
import com.mpango.Util.objects.LoanRepaymentTransaction;
import com.mpango.bus.LoanBUS;
import com.mpango.bus.LoanDTO;
import com.mpango.dao.idao.I_Loan;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import java.sql.CallableStatement;

/**
 *
 * @author jmulutu
 */
public class LoanDAO implements I_Loan {

    @Override
    public int LoanRepaymentSP(String loanRef, String trxRef, BigDecimal repayAmount, String FTNumber, Connection con) {
        int result = -1;
        try {
            if (con != null) {
                CallableStatement cstmt = null;
                cstmt = (CallableStatement) con
                        .prepareCall("{call LoanRepayment(?,?,?,?,?,?,?,?)}");                
                cstmt.setString(1, loanRef);
                cstmt.setString(2, trxRef);
                cstmt.setBigDecimal(3, repayAmount);
                cstmt.setString(4, "1");
                cstmt.setString(5, "1111111111");
                cstmt.setString(6, "222222");
                cstmt.setString(7, FTNumber);
                cstmt.registerOutParameter(8, java.sql.Types.INTEGER);
                cstmt.execute();

                result = cstmt.getInt(8);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        }

        return result;
    }

    public int SQLInsert(String SQL, Connection con) {
        int result = 0;
        Statement st = null;
        try {
            st = con.prepareCall(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return result;
    }

    public int SQLUpdate(String SQL, Connection con) {
        int result = 0;
        Statement sttm = null;
        try {
            sttm = con.prepareStatement(SQL);
            int rowsUpdated = sttm.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
            sttm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(sttm);
            //DbUtils.closeQuietly(con);
        }
        return result;
    }

    public BigDecimal calculateLoanPenalty(LoanDTO loandetails) {
        BigDecimal penalty = null;

        MathContext mc = MathContext.DECIMAL64;

        BigDecimal currentOutstandingAmount = loandetails.getEmiOutstandingAmount();
        BigDecimal currentAmountReceived = loandetails.getEmiAmountReceived();
        BigDecimal currentTotalReceivable = loandetails.getEmiTotalReceivable();

        BigDecimal penaltyAmount = currentTotalReceivable.subtract(currentAmountReceived, mc);
        penaltyAmount = penaltyAmount.add(currentOutstandingAmount, mc);

        double penaltyRatePercent = 0.1;

        BigDecimal penaltyRate = new BigDecimal(penaltyRatePercent, mc);

        penalty = penaltyAmount.multiply(penaltyRate, mc);

        return penalty;
    }

    public int isDefaultEMI(LoanDTO loandetails) {
        int result = 0;

        BigDecimal currentOutstandingAmount = loandetails.getEmiOutstandingAmount();
        BigDecimal currentAmountReceived = loandetails.getEmiAmountReceived();
        BigDecimal currentTotalReceivable = loandetails.getEmiTotalReceivable();

        int res = currentTotalReceivable.compareTo(currentAmountReceived);

        if (res == 1) { // first vallue is greater , then apply penalty 
            result = 1;
        }
        return result;
    }

    public int createLoanPenalty(LoanDTO loandetails, BigDecimal penaltyAmount, Connection con) {
        int result = 0;
        MathContext mc = MathContext.DECIMAL64;
        int penaltyTypeID = 1;
        String SQL = "INSERT INTO `loan_penalties`( "
                + "`PENALTY_TYPE_ID`, "
                + "`LOAN_REF`, "
                + "`PENALTY_AMOUNT` "
                + ") "
                + "VALUES ( "
                + "'" + penaltyTypeID + "',"
                + "'" + loandetails.getLoanRef() + "', "
                + "'" + penaltyAmount + "' "
                + ")";
        int insertResult = this.SQLInsert(SQL, con);
        if (insertResult == 1) {
            BigDecimal currenttotalpenalty = loandetails.getTotalPenalties();
            BigDecimal newtotalpenalty = currenttotalpenalty.add(penaltyAmount, mc);

            // Update disbursement to reflect the penalty
            String SQL_PENALTY_UPDATE = "UPDATE `loan_disbursements` "
                    + "SET total_penalties = " + newtotalpenalty + " "
                    + "WHERE LOAN_REF='" + loandetails.getLoanRef() + "'";

            int updateResult = this.SQLUpdate(SQL_PENALTY_UPDATE, con);
            if (updateResult == 1) {
                result = 1;
            }
        }
        return result;
    }

    @Override
    public int updateRepayment(LoanRepaymentTransaction loanrepayment, Connection con) {
        int result = 0;
        String SQL = "UPDATE loan_repayments "
                + "SET status='PROCESSED' "
                + "WHERE ID = " + loanrepayment.getRepaymentID() + " "
                + "AND LOAN_REF='" + loanrepayment.getLoanRef() + "'";

        int updateResult = this.SQLUpdate(SQL, con);
        if (updateResult == 1) {
            result = 1;
        }
        return result;
    }

    @Override
    public List<LoanRepaymentTransaction> getAllLoanRepayments(Connection con) {
        List<LoanRepaymentTransaction> LoanRepaymentList = new ArrayList<>();
        String SQL = "SELECT * "
                + "FROM loan_repayments lr, loan_disbursements ld "
                + "WHERE lr.STATUS='PENDING' AND lr.LOAN_REF=ld.LOAN_REF ";
        
        System.out.println("getAllLoanRepayments SQL--> " +  SQL);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                LoanRepaymentTransaction loanrepayment = new LoanRepaymentTransaction();
                loanrepayment.setRepaymentID(rs.getInt("ID"));
                loanrepayment.setEMIInstallmentID(rs.getInt("INSTALLMENT_NO"));
                loanrepayment.setMSISDN(rs.getString("MSISDN"));
                loanrepayment.setLoanRef(rs.getString("LOAN_REF"));
                loanrepayment.setRepaymentAmount(rs.getBigDecimal("AMOUNT"));
                loanrepayment.setRepaymentDate(rs.getDate("PAYMENT_DATE"));
                loanrepayment.setOtherDetails(rs.getString("OTHER_DETAILS"));
                loanrepayment.setStatus(rs.getString("STATUS"));
                loanrepayment.setRepaymentType(rs.getString("REPAYMENT_TYPE"));
                loanrepayment.setTransactionRefNum(rs.getString("TRX_REF_NUMBER"));
                loanrepayment.setDebitAccount(rs.getString("DEBIT_ACCOUNT"));
                loanrepayment.setCreditAccount(rs.getString("CREDIT_ACCOUNT"));
                loanrepayment.setInterestRate(rs.getBigDecimal("INTEREST_RATE"));

                LoanRepaymentList.add(loanrepayment);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return LoanRepaymentList;
    }

    @Override
    public List<LoanApplicationTransaction> getAllLoanApplications(Connection con) {
        List<LoanApplicationTransaction> loanApplicationsList = new ArrayList<>();
        String SQL = "SELECT * "
                + "FROM loan_applications la, interest_rates ir, fosa_account fa "
                + "WHERE la.STATUS='PENDING' "
                + "AND  la.LOAN_TYPE_ID=ir.LOAN_TYPE_ID "
                + "AND la.DURATION=ir.DURATION "
                + "AND la.customer_id=fa.customer_id";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                LoanApplicationTransaction loanapplication = new LoanApplicationTransaction();
                loanapplication.setCustomerID(rs.getInt("CUSTOMER_ID"));
                loanapplication.setTransactionRef(rs.getString("TRX_REF_NUMBER"));
                loanapplication.setLoanRef(rs.getString("LOAN_REF"));
                loanapplication.setLoanTypeID(rs.getInt("LOAN_TYPE_ID"));
                loanapplication.setAmount(rs.getBigDecimal("AMOUNT"));
                loanapplication.setRepaymentDuration(rs.getInt("DURATION"));
                loanapplication.setInterestRate(rs.getBigDecimal("RATE"));
                loanapplication.setInterestRateType(rs.getString("RATE_TYPE"));
                loanapplication.setCreditAccount(rs.getString("ACCOUNT_NO"));
                loanApplicationsList.add(loanapplication);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return loanApplicationsList;
    }

    @Override
    public void updateEMISchedule(Connection con) {

        MathContext mc = MathContext.DECIMAL64;

        String SQL = "SELECT loan_ref "
                + "FROM loan_emi_receive "
                + "WHERE SCHEDULE='CURRENT' "
                + "AND date_format( emi_date,'%Y-%m-%d %T') < date_format( NOW(),'%Y-%m-%d %T')";

        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                LoanDTO loanDetails = null;
                String loanRef = "";
                loanRef = rs.getString("LOAN_REF");
                loanDetails = this.getLoanByRef(loanRef, con);

                /* Check if the EMI is in default */
                int isDefaultEMI = this.isDefaultEMI(loanDetails);
                BigDecimal penaltyAmount = BigDecimal.ZERO;

                if (isDefaultEMI == 1) {

                    penaltyAmount = this.calculateLoanPenalty(loanDetails);
                    int recordPenaltyResult = this.createLoanPenalty(loanDetails, penaltyAmount, con); // record penalty

                    if (recordPenaltyResult == 1) {
                        String SQL_UPDATE_PENALTY = "UPDATE loan_emi_receive "
                                + "SET penalty=" + penaltyAmount + " "
                                + "WHERE installment_no = " + loanDetails.getCurrentEMIInstallmentNumber()
                                + " AND loan_ref = '" + loanRef + "'";
                        int penaltyUpdateResult = this.SQLUpdate(SQL_UPDATE_PENALTY, con);
                        if (penaltyUpdateResult == 1) {
                            //
                        }
                    }
                }

                int loanTerms = loanDetails.getLoanTerms();
                int currentEMIIDInstallmentNumber = loanDetails.getCurrentEMIInstallmentNumber();

                if (currentEMIIDInstallmentNumber < loanTerms) {

                    int newEMIIDInstallmentNumber = currentEMIIDInstallmentNumber + 1;
                    int oldEMIResult = this.updateEMIschedule(currentEMIIDInstallmentNumber, loanRef, "CLOSED", con);

                    if (oldEMIResult == 1) {

                        int newEMIResult = this.updateEMIschedule(newEMIIDInstallmentNumber, loanRef, "CURRENT", con);

                        if (isDefaultEMI == 1 && newEMIResult == 1) {

                            BigDecimal currentOutstandingAmount = loanDetails.getEmiOutstandingAmount();

                            LoanDTO loanDetailsUpdated = this.getLoanByRef(loanRef, con);

                            BigDecimal updatedOutstandingAmount = loanDetailsUpdated.getEmiOutstandingAmount();
                            BigDecimal nextNewOutstandingAmount = currentOutstandingAmount.add(updatedOutstandingAmount, mc);
                            nextNewOutstandingAmount = nextNewOutstandingAmount.add(penaltyAmount, mc);

                            String SQL_UPDATE_NEW_OUTSTANDING = "UPDATE loan_emi_receive "
                                    + "SET outstanding_amount=" + nextNewOutstandingAmount + " "
                                    + "WHERE installment_no = " + newEMIIDInstallmentNumber + " AND loan_ref = '" + loanRef + "'";

                            int newOutstandingUpdateResult = this.SQLUpdate(SQL_UPDATE_NEW_OUTSTANDING, con);

                            if (newOutstandingUpdateResult == 1) {
                                //
                            }
                        }
                    }
                } else {

                    int oldEMIResult = this.updateEMIschedule(currentEMIIDInstallmentNumber, loanRef, "CLOSED", con);

                    if (oldEMIResult == 1) {

                        BigDecimal currentOutstandingAmount = loanDetails.getEmiOutstandingAmount();
                        BigDecimal nextNewOutstandingAmount = currentOutstandingAmount.add(penaltyAmount, mc);

                        String SQL_UPDATE_NEW_OUTSTANDING_PENALTY = "UPDATE loan_emi_receive "
                                + "SET penalty=" + penaltyAmount + ", "
                                + "outstanding_amount=" + nextNewOutstandingAmount + " "
                                + "WHERE installment_no = " + loanDetails.getCurrentEMIInstallmentNumber() + " AND loan_ref = '" + loanRef + "'";

                        int newOutstandingUpdateResult = this.SQLUpdate(SQL_UPDATE_NEW_OUTSTANDING_PENALTY, con);

                        if (newOutstandingUpdateResult == 1) {
                            // update loan_disbursement table as a DEFAULT loan
                            String SQL_DEFAULT_UPDATE = "UPDATE `loan_disbursements` "
                                    + "SET status = 'DEFAULT' "
                                    + "WHERE LOAN_REF='" + loanDetails.getLoanRef() + "'";

                            int updateDefaultResult = this.SQLUpdate(SQL_DEFAULT_UPDATE, con);
                            if (updateDefaultResult == 1) {
                                // update successfull
                            }
                        }
                    }
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
    }

    public int updateEMIschedule(int EMIIDInstallmentNumber, String loanRef, String status, Connection con) {
        int result = 0;
        String SQL = "UPDATE loan_emi_receive "
                + "SET schedule='" + status + "' "
                + "WHERE INSTALLMENT_NO = " + EMIIDInstallmentNumber + " "
                + "AND LOAN_REF='" + loanRef + "'";

        int updateResult = this.SQLUpdate(SQL, con);
        if (updateResult == 1) {
            result = 1;
        }
        return result;
    }

    @Override
    public List<String> getDueLoans(int dueInDays, Connection con) {
        List<String> dueLoanRefs = new ArrayList<>();
        //int dueInSeconds = dueInDays * 24 * 60 * 60;
        String SQL = "SELECT loan_ref FROM loan_disbursements WHERE DATEDIFF(DATE(due_date), DATE(NOW())) =" + dueInDays + "";
        //String SQL = "SELECT loan_ref FROM loan_disbursements WHERE TIMESTAMPDIFF(second,NOW(),due_date) = " + dueInSeconds + "";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                dueLoanRefs.add(rs.getString("LOAN_REF"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }

        return dueLoanRefs;
    }

    @Override
    public int updateEMIPayment(LoanRepaymentTransaction loanrepayment, Connection con) {
        int result = 0;

        MathContext mc = MathContext.DECIMAL64;

        String loanRef = loanrepayment.getLoanRef();
        LoanDTO loanDetails = this.getLoanByRef(loanRef, con);
        BigDecimal receivedAmount = loanrepayment.getRepaymentAmount();
        BigDecimal totalAmountReceived = loanDetails.getEmiAmountReceived();
        BigDecimal emiAmount = loanDetails.getEmiAmount();
        BigDecimal penalty = loanDetails.getEmiPenalty();

        BigDecimal newTotalAmountReceived = receivedAmount.add(totalAmountReceived, mc);
        BigDecimal newTotalReceivable = emiAmount.add(penalty, mc);

        // check if new amountReceived is greater than newTotalReceivable
        /* compare repayAmount with loanBalance */
        int res = newTotalAmountReceived.compareTo(newTotalReceivable);
        String SQL = "";

        BigDecimal to_update_amount_received = null;
        BigDecimal to_update_total_receivable = null;
        BigDecimal to_update_outstanding_amount = null;
        BigDecimal surplusPayment = null;

        if (res == 0) { // equal
            BigDecimal newOutstandingAmount = BigDecimal.ZERO;
            to_update_amount_received = newTotalAmountReceived;
            to_update_total_receivable = newTotalReceivable;
            to_update_outstanding_amount = newOutstandingAmount;
        } else if (res == 1) { // first vallue is greater 
            BigDecimal newOutstandingAmount = BigDecimal.ZERO;
            surplusPayment = newTotalAmountReceived.subtract(newTotalReceivable, mc);

            to_update_amount_received = newTotalReceivable;
            to_update_total_receivable = newTotalReceivable;
            to_update_outstanding_amount = newOutstandingAmount;
        } else if (res == -1) { // second value is greater
            BigDecimal newOutstandingAmount = newTotalReceivable.subtract(newTotalAmountReceived, mc);
            to_update_amount_received = newTotalAmountReceived;
            to_update_total_receivable = newTotalReceivable;
            to_update_outstanding_amount = newOutstandingAmount;
        }

        SQL = "UPDATE loan_emi_receive "
                + "SET amount_received=" + to_update_amount_received + ", "
                + "total_receivable=" + to_update_total_receivable + ", "
                + "outstanding_amount=" + to_update_outstanding_amount + " "
                + "WHERE installment_no = " + loanDetails.getCurrentEMIInstallmentNumber() + " AND loan_ref = '" + loanRef + "'";

        Statement sttm = null;
        try {
            sttm = con.prepareStatement(SQL);
            int rowsUpdated = sttm.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
                if (res == 1 || res == 0) {

                    LoanDTO loanDetailsUpdated = this.getLoanByRef(loanRef, con);
                    int loanTerms = loanDetailsUpdated.getLoanTerms();
                    int currentEMIIDInstallmentNumber = loanDetailsUpdated.getCurrentEMIInstallmentNumber();

                    if (currentEMIIDInstallmentNumber < loanTerms) {
                        int newEMIIDInstallmentNumber = currentEMIIDInstallmentNumber + 1;
                        int oldEMIResult = this.updateEMIschedule(currentEMIIDInstallmentNumber, loanRef, "CLOSED", con);
                        int newEMIResult = this.updateEMIschedule(newEMIIDInstallmentNumber, loanRef, "CURRENT", con);
                    } else {
                        int oldEMIResult = this.updateEMIschedule(currentEMIIDInstallmentNumber, loanRef, "CLOSED", con);
                    }
                    /*  record surplus repayment as a new repayment */
                    if (res == 1) {
                        this.recordSurplusRepayment(loanDetailsUpdated, surplusPayment, con);
                    }
                }
            }
            sttm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(sttm);
            //DbUtils.closeQuietly(con);
        }
        return result;
    }

    @Override
    public int getInstallmentNo(String loanRef, Connection con) {
        int emiID = 1;
        String SQL = "SELECT * FROM loan_emi_receive WHERE loan_ref = '" + loanRef + "' AND schedule ='CURRENT'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                emiID = rs.getInt("EMI_ID");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return emiID;
    }

    @Override
    public BigDecimal calculateEMI(LoanApplicationTransaction loanApplication, Connection con) {
        BigDecimal emi = null;
        MathContext mc = MathContext.DECIMAL64;
        String interestType = loanApplication.getInterestRateType();
        BigDecimal interestRate = loanApplication.getInterestRate();

        BigDecimal principal = loanApplication.getAmount();
        int repaymentPeriod = loanApplication.getRepaymentDuration();
        interestRate = interestRate.divide(new BigDecimal(100, mc));

        if (interestType.equals("REDUCING_BALANCE")) {
            BigDecimal numerator = principal.multiply(interestRate, mc);
            BigDecimal denominator = interestRate.add(new BigDecimal(1, mc), mc);
            denominator = denominator.pow(-repaymentPeriod, mc);
            denominator = (new BigDecimal(1, mc)).subtract(denominator, mc);
            emi = numerator.divide(denominator, mc);
        } else if (interestType.equals("FLAT")) {
            BigDecimal multiplier = interestRate.add(new BigDecimal("1.0", mc), mc);
            BigDecimal amount = principal.multiply(multiplier, mc);
            emi = amount.divide(new BigDecimal(repaymentPeriod, mc), mc);
        }

        return emi;
    }

    /*
     @recordRepayment
     */
    @Override
    public int recordRepayment(LoanDTO loanDetails, BigDecimal repayAmount, String transactionRef, Connection con) {
        int result = 0;
        String SQL = "INSERT INTO `loan_repayments`( "
                + "`TRX_REF_NUMBER`, "
                + "`INSTALLMENT_NO`, "
                + "`MSISDN`, "
                + "`LOAN_REF`, "
                + "`AMOUNT`, "
                + "`PAYMENT_DATE`, "
                + "`DEBIT_ACCOUNT`, "
                + "`CREDIT_ACCOUNT` "
                + ") "
                + "VALUES ( "
                + "'" + transactionRef + "', "
                + "'" + loanDetails.getCurrentEMIInstallmentNumber() + "',"
                + "'" + loanDetails.getCustomerMSISDN() + "', "
                + "'" + loanDetails.getLoanRef() + "', "
                + "'" + repayAmount + "', "
                + "NOW(), "
                + "'" + loanDetails.getRepaymentAccount() + "', "
                + "'" + loanDetails.getControlAccount() + "' "
                + ")";
        System.out.println(" recordRepayment -- " + SQL);
        int insertResult = this.SQLInsert(SQL, con);
        if (insertResult == 1) {
            result = 1;
        }

        return result;
    }

    public int recordSurplusRepayment(LoanDTO loanDetails, BigDecimal repayAmount, Connection con) {
        int result = 0;
        String SQL = "INSERT INTO `loan_repayments`( "
                + "`EMI_INSTALLMENT_ID`, "
                + "`MSISDN`, "
                + "`LOAN_REF`, "
                + "`AMOUNT`, "
                + "`PAYMENT_DATE`, "
                + "`REPAYMENT_TYPE` "
                + ") "
                + "VALUES ( "
                + "'" + loanDetails.getCurrentEMIInstallmentNumber() + "',"
                + "'" + loanDetails.getCustomerMSISDN() + "', "
                + "'" + loanDetails.getLoanRef() + "', "
                + "'" + repayAmount + "', "
                + "NOW(), "
                + "'SURPLUS' "
                + ")";

        int insertResult = this.SQLInsert(SQL, con);
        if (insertResult == 1) {
            result = 1;
        }

        return result;
    }

    @Override
    public int updateLoanApplicationStatus(String loanRefNumber, String status, Connection con) {
        int result = 0;
        String SQL = "UPDATE loan_applications SET status='" + status + "', date_disbursed= NOW() "
                + "WHERE loan_ref = '" + loanRefNumber + "'";

        int updateResult = this.SQLUpdate(SQL, con);
        if (updateResult == 1) {
            result = 1;
        }
        return result;
    }

    @Override
    public int createEmiSchedule(LoanApplicationTransaction loanApplication, Connection con) {
        int result = 0;
        MathContext mc = MathContext.DECIMAL64;
        int repaymentPeriod = loanApplication.getRepaymentDuration();
        BigDecimal emi = this.calculateEMI(loanApplication, con);
        System.out.println("emi: " + emi);
        BigDecimal interestRate = loanApplication.getInterestRate();
        System.out.println("interestRate: " + interestRate);
        interestRate = interestRate.divide(new BigDecimal(100, mc));
        System.out.println("interestRate: " + interestRate);

        BigDecimal numerator = emi.multiply(new BigDecimal(repaymentPeriod, mc), mc);
        BigDecimal denominator = interestRate.add(new BigDecimal("1.0", mc), mc);
        BigDecimal emi_principal = numerator.divide(denominator, mc);
        BigDecimal emi_interest = emi.subtract(emi_principal, mc);

        String SQL = "INSERT INTO `loan_emi_receive` ("
                + "`CUSTOMER_ID`, "
                + "`INSTALLMENT_NO`, "
                + "`LOAN_REF`, "
                + "`EMI_AMOUNT`, "
                + "`EMI_PRINCIPAL`, "
                + "`EMI_INTEREST`, "
                + "`EMI_DATE`, "
                + "`PENALTY`, "
                + "`OUTSTANDING_AMOUNT`, "
                + "`OUTSTANDING_PRINCIPAL`, "
                + "`OUTSTANDING_INTEREST`, "
                + "`TOTAL_RECEIVABLE`, "
                + "`AMOUNT_RECEIVED`,"
                + "`SCHEDULE`"
                + ") VALUES";

        for (int i = 1; i <= repaymentPeriod; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, i);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdfDate.format(cal.getTime());
            String emi_sql = "( "
                    + "" + loanApplication.getCustomerID() + ", "
                    + "" + i + ", "
                    + "'" + loanApplication.getLoanRef() + "', "
                    + "" + emi + ", "
                    + "" + emi_principal + ", "
                    + "" + emi_interest + ", "
                    + "'" + strDate + "', "
                    + "'0', "
                    + "'0', "
                    + "" + emi_principal + ", "
                    + "" + emi_interest + ", "
                    + "" + emi + ", "
                    + "'0'";

            if (i == 1) {
                emi_sql = emi_sql + ",'CURRENT'";
            } else {
                emi_sql = emi_sql + ",''";
            }

            emi_sql = emi_sql + ")";

            if (i < repaymentPeriod) {
                emi_sql = emi_sql + ",";
            }
            SQL = SQL + emi_sql;
        }

        System.out.println("createEmiSchedule:  " + SQL);

        int insertResult = this.SQLInsert(SQL, con);
        if (insertResult == 1) {
            result = 1;
        }
        return result;
    }

    @Override
    public int recordDisbursement(LoanApplicationTransaction loanApplication, Connection con) {

        int result = 0;
        MathContext mc = MathContext.DECIMAL64;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, loanApplication.getRepaymentDuration());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(cal.getTime());

        // full loan calculations
        // emi calculations
        BigDecimal emi = this.calculateEMI(loanApplication, con);
        BigDecimal loanTerm = new BigDecimal(loanApplication.getRepaymentDuration(), mc);
        BigDecimal loanBalance = emi.multiply(loanTerm, mc);

        // calculate interest receivable
        BigDecimal interestAmountTotal = loanBalance.subtract(loanApplication.getAmount(), mc);

        String SQL = "INSERT INTO `loan_disbursements`("
                + "`CUSTOMER_ID`, "
                + "`LOAN_REF`, "
                + "`PRINCIPAL_AMOUNT`, "
                + "`INTEREST_RATE`, "
                + "`LOAN_TERM`, "
                + "`INTEREST_AMOUNT`, "
                + "`TOTAL_LOAN_AMOUNT`, "
                + "`EMI_AMOUNT`, "
                + "`DATE_DISBURSED`, "
                + "`DUE_DATE`, "
                + "`LOAN_BALANCE`)"
                + "VALUES ("
                + loanApplication.getCustomerID() + ",'"
                + loanApplication.getLoanRef() + "',"
                + loanApplication.getAmount() + ","
                + loanApplication.getInterestRate() + ","
                + loanApplication.getRepaymentDuration() + ","
                + interestAmountTotal + ", "
                + loanBalance + ", "
                + emi + ", "
                + "NOW(), '"
                + strDate + "', "
                + loanBalance + ""
                + ")";

        int insertResult = this.SQLInsert(SQL, con);
        if (insertResult == 1) {
            result = 1;
        }

        return result;
    }

    @Override
    public BigDecimal getInterestAmount(BigDecimal interestRate, BigDecimal loanAmount) {
        BigDecimal rateInDecimal = interestRate.divide(new BigDecimal(100));
        MathContext mc = MathContext.DECIMAL64;
        BigDecimal amount = loanAmount.multiply(rateInDecimal, mc);
        return amount;
    }

    @Override
    public BigDecimal getInterestRate(String loanRefNumber, int repaymentPeriod, Connection con) {
        BigDecimal rate = null;
        String SQL = "SELECT r.rate, l.loan_ref, r.duration \n"
                + "FROM loan_applications l, interest_rates r \n"
                + "WHERE r.loan_type_id = l.loan_type_id  \n"
                + "AND l.loan_ref= '" + loanRefNumber + "'\n"
                + "AND r.duration = " + repaymentPeriod;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                rate = rs.getBigDecimal("rate");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rate;
    }

    /*
     @LoanRepaymentTransaction
     */
    @Override
    public int LoanRepayment(LoanRepaymentTransaction loanrepayment, Connection con) {
        int result = 0;
        String repaymentType = loanrepayment.getRepaymentType();

        if (repaymentType.equalsIgnoreCase("SURPLUS")) {
            result = 1;
        } else {
            MathContext mc = MathContext.DECIMAL64;
            String loanRef = loanrepayment.getLoanRef();
            LoanDTO loanDetails = LoanBUS.getLoanByRef(loanRef, con);
            BigDecimal currentLoanBalance = loanDetails.getLoanBalance();
            BigDecimal currentTotalRepayments = loanDetails.getTotalRepayments();
            BigDecimal repayAmount = loanrepayment.getRepaymentAmount();
            BigDecimal newTotalRepayments = repayAmount.add(currentTotalRepayments, mc);
            BigDecimal newLoanBalance = currentLoanBalance.subtract(repayAmount, mc);

            // compare curentloan balance with repayamount
            int res = currentLoanBalance.compareTo(repayAmount);

            BigDecimal loan_balance_to_update = null;

            if (res == 0) { // equal
                loan_balance_to_update = currentLoanBalance.subtract(repayAmount, mc);
            } else if (res == 1) { // first vallue is greater 
                loan_balance_to_update = currentLoanBalance.subtract(repayAmount, mc);
            } else if (res == -1) { // second value is greater
                loan_balance_to_update = BigDecimal.ZERO;
            }

            String SQL = "UPDATE `loan_disbursements` "
                    + "SET loan_balance = " + loan_balance_to_update + ", "
                    + "total_repayments= " + newTotalRepayments + " "
                    + "WHERE LOAN_REF='" + loanRef + "'";

            int updateResult = this.SQLUpdate(SQL, con);
            if (updateResult == 1) {
                // Update disbursement
                LoanDTO loanDetailsUpdated = LoanBUS.getLoanByRef(loanRef, con);
                BigDecimal loanBalanceUpdated = loanDetailsUpdated.getLoanBalance();

                if (loanBalanceUpdated.compareTo(BigDecimal.ZERO) == 0) {
                    String SQL_UPDATED = "UPDATE `loan_disbursements` "
                            + "SET status = 'REPAID' "
                            + "WHERE LOAN_REF='" + loanRef + "'";

                    int updateResultRepaid = this.SQLUpdate(SQL_UPDATED, con);
                }
                result = 1;
            }
        }
        return result;
    }

    private int CheckIfLoanRepaid(String loanRef, Connection con) {
        int result = 0;
        String SQL = "SELECT * FROM loan_disbursements where LOAN_REF = '" + loanRef + "'";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                //result = rs.getBigDecimal("total_repayments");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public BigDecimal getLoanTotalRepayments(String loanRefNumber, Connection con) {
        BigDecimal result = null;
        String SQL = "SELECT total_repayments FROM loan_disbursements WHERE loan_ref= '" + loanRefNumber + "'";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                result = rs.getBigDecimal("total_repayments");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public BigDecimal getLoanBalance(String loanRefNumber, Connection con) {
        BigDecimal result = null;
        String SQL = "SELECT loan_balance FROM loan_disbursements WHERE loan_ref= '" + loanRefNumber + "'";
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                result = rs.getBigDecimal("loan_balance");
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public LoanDTO getLoanByID(int loanID, Connection con) {
        LoanDTO loan = new LoanDTO();

        String SQL = "select * from loan_disbursements where ID = " + loanID + "";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                loan.setLoanID(rs.getInt("ID"));
                loan.setLoanRef(rs.getString("LOAN_REF"));
                loan.setDateDisbursed(rs.getString("DATE_DISBURSED"));
                loan.setDueDate(rs.getString("DUE_DATE"));
                loan.setAmount(rs.getBigDecimal("PRINCIPAL_AMOUNT"));
                loan.setLoanTerm(rs.getInt("LOAN_TERM"));
                loan.setLoanBalance(rs.getBigDecimal("LOAN_BALANCE"));
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    @Override
    public LoanDTO getLoanByRef(String loanRef, Connection con) {
        LoanDTO loan = new LoanDTO();
        String SQL = "SELECT * "
                + "FROM loan_disbursements ld, loan_applications la, customers c, interest_rates ir, loan_types lt, loan_emi_receive emi "
                + "WHERE ld.LOAN_REF = '" + loanRef + "' "
                + "AND ld.LOAN_REF=la.LOAN_REF "
                + "AND la.loan_type_id = ir.loan_type_id "
                + "AND c.customer_id = la.customer_id "
                + "AND ld.LOAN_REF = emi.loan_ref "
                + "AND emi.SCHEDULE = 'CURRENT' "
                + "AND ir.rate=ld.interest_rate AND lt.loan_type_id=la.loan_type_id";
        Statement st;

        try {
            st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                loan.setLoanID(rs.getInt("ID"));
                loan.setLoanTerm(rs.getInt("LOAN_TERM"));
                loan.setLoanRef(rs.getString("LOAN_REF"));
                loan.setDateDisbursed(rs.getString("DATE_DISBURSED"));
                loan.setDueDate(rs.getString("DUE_DATE"));
                loan.setCustomerID(rs.getInt("CUSTOMER_ID"));
                loan.setCustomerMSISDN(rs.getString("MSISDN"));
                loan.setLoanTerm(rs.getInt("DURATION"));
                loan.setLoanTerms(rs.getInt("LOAN_TERM"));
                loan.setLoanBalance(rs.getBigDecimal("LOAN_BALANCE"));
                loan.setTotalRepayments(rs.getBigDecimal("TOTAL_REPAYMENTS"));
                loan.setTotalRepayments(rs.getBigDecimal("TOTAL_LOAN_AMOUNT"));
                loan.setInterestRate(rs.getBigDecimal("INTEREST_RATE"));
                loan.setInterestRateType(rs.getString("RATE_TYPE"));
                loan.setLoanType(rs.getString("LOAN_TYPE_DESC"));
                loan.setLoanTypeID(rs.getInt("LOAN_TYPE_ID"));
                loan.setPrincipal(rs.getBigDecimal("PRINCIPAL_AMOUNT"));
                loan.setTrxRefNumber(rs.getString("TRX_REF_NUMBER"));
                loan.setCurrentEMIID(rs.getInt("EMI_ID"));
                loan.setEmiAmount(rs.getBigDecimal("EMI_AMOUNT"));
                loan.setEmiDueDate(rs.getDate("EMI_DATE"));
                loan.setEmiOutstandingAmount(rs.getBigDecimal("OUTSTANDING_AMOUNT"));
                loan.setEmiPenalty(rs.getBigDecimal("PENALTY"));
                loan.setEmiSchedule(rs.getString("SCHEDULE"));
                loan.setEmiTotalReceivable(rs.getBigDecimal("TOTAL_RECEIVABLE"));
                loan.setTotalPenalties(rs.getBigDecimal("TOTAL_PENALTIES"));
                loan.setEmiAmountReceived(rs.getBigDecimal("AMOUNT_RECEIVED"));
                loan.setCurrentEMIInstallmentNumber(rs.getInt("INSTALLMENT_NO"));

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loan;
    }

    @Override
    public LoanDTO getLoanApplicationByLoanRef(String loanRef, Connection con) {
        LoanDTO loan = new LoanDTO();
        String SQL = "SELECT * "
                + "FROM loan_applications la, interest_rates ir, loan_types lt "
                + "WHERE la.LOAN_REF = '" + loanRef + "' "
                + "AND la.loan_type_id = ir.loan_type_id "
                + "AND lt.loan_type_id=la.loan_type_id "
                + "AND la.duration = ir.duration";
        Statement st;

        try {
            st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                loan.setLoanID(rs.getInt("ID"));
                loan.setCustomerID(rs.getInt("CUSTOMER_ID"));
                loan.setTrxRefNumber(rs.getString("TRX_REF_NUMBER"));
                loan.setLoanRef(rs.getString("LOAN_REF"));
                loan.setLoanTypeID(rs.getInt("LOAN_TYPE_ID"));
                loan.setAmount(rs.getBigDecimal("AMOUNT"));
                loan.setLoanTerm(rs.getInt("DURATION"));
                //loan.setRequestDate(rs.getString("REQUEST_DATE"));                
                loan.setDateDisbursed(rs.getString("DATE_DISBURSED"));
                loan.setStatus(rs.getString("STATUS"));
                loan.setLoanTypeID(rs.getInt("LOAN_TYPE_ID"));
                loan.setLoanType(rs.getString("LOAN_TYPE_DESC"));
                loan.setInterestRate(rs.getBigDecimal("RATE"));
                loan.setInterestRateType(rs.getString("RATE_TYPE"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loan;
    }

    @Override
    public int loanApplication(LoanApplicationTransaction loanapplication, Connection con) {
        int result = 0;

        String SQL = "INSERT INTO `loan_applications`( `CUSTOMER_ID`, `TRX_REF_NUMBER`, `LOAN_REF`, `LOAN_TYPE_ID`, `AMOUNT`, `DURATION`, `REQUEST_DATE` ) "
                + "VALUES ( "
                + "" + loanapplication.getCustomer().getCustomerID() + ","
                + "'" + loanapplication.getTransactionRef() + "',"
                + "'" + loanapplication.getLoanRef() + "',"
                + "" + loanapplication.getLoanTypeID() + ","
                + "" + loanapplication.getAmount() + ","
                + "" + loanapplication.getRepaymentDuration() + ","
                + "NOW() "
                + ")";

        int insertResult = this.SQLInsert(SQL, con);
        if (insertResult == 1) {
            result = 1;
        }

        return result;
    }

}
