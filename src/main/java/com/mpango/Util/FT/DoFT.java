/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.FT;

import com.mpango.Util.objects.Account;
import com.mpango.Util.objects.FinancialTransaction;
import com.mpango.bus.AccountBUS;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;
import com.mpango.Util.IdGenerator;

/**
 *
 * @author jmulutu
 */
public class DoFT {
    
    private static IdGenerator randomGen = new IdGenerator();
    
    public static int doLoanApplicationFT(FinancialTransaction FT, Connection con) {
        int result = 0;

        MathContext mc = MathContext.DECIMAL64;

        Account crAccountDetails = AccountBUS.getAccount(FT.getCreditAccount(), con);
        BigDecimal crAccountBalance = crAccountDetails.getAccountBalance();
        String crAccountNumber = crAccountDetails.getAccountNumber();
        
        System.out.println("crAccountBalance: " + crAccountBalance + " crAccountNumber: " + crAccountNumber);

        Account drAccountDetails = AccountBUS.getControlAccount(FT.getDebitAccount() , con);
        BigDecimal drAccountBalance = drAccountDetails.getAccountBalance();
        String drAccountNumber = drAccountDetails.getAccountNumber();
        
        System.out.println("drAccountBalance: " + drAccountBalance + " drAccountNumber: " + drAccountNumber);

        BigDecimal amount = FT.getAmount();

        BigDecimal newDebitAmount = drAccountBalance.subtract(amount, mc);        

        BigDecimal newCreditAmount = crAccountBalance.add(amount, mc);
        

        int drResult = doCreditDebit(drAccountNumber, newDebitAmount, "CONTROL", con);
        int crResult = doCreditDebit(crAccountNumber, newCreditAmount, "FOSA", con);
        int doFtresult = doFTxn(FT, con);
        
        if (drResult == 1 && crResult == 1 && doFtresult == 1 ) {
            result = 1;
        }

        return result;
    }

    public static int doLoanRepaymentFT(FinancialTransaction FT, Connection con) {
        int result = 0;

        MathContext mc = MathContext.DECIMAL64;

        Account drAccount = AccountBUS.getAccount(FT.getDebitAccount(), con);
        BigDecimal drAccountBalance = drAccount.getAccountBalance(); 
        System.out.println("drAccountBalance " + drAccountBalance);
        String drAccountNumber = drAccount.getAccountNumber();
        System.out.println("drAccountNumber " + drAccountNumber);

        Account crAccount = AccountBUS.getControlAccount(FT.getCreditAccount(), con);
        BigDecimal crAccountBalance = crAccount.getAccountBalance();
        System.out.println("crAccountBalance " + crAccountBalance);
        String crAccountNumber = crAccount.getAccountNumber();
        
        Account PLAccount = AccountBUS.getControlAccount("222222", con);
        BigDecimal PLAccountBalance = PLAccount.getAccountBalance();
        String PLAccountNumber = PLAccount.getAccountNumber();
        
        BigDecimal amount = FT.getAmount();
        System.out.println("amount " + amount);
   
        
        BigDecimal newDebitAmount = drAccountBalance.subtract(amount, mc);        
        System.out.println("newDebitAmount " + newDebitAmount);
        
        BigDecimal interestRate =  FT.getInterestRate();        
        interestRate = interestRate.divide(new BigDecimal(100, mc));
        System.out.println("interestRate " + interestRate);

        BigDecimal interestAmount = amount.multiply(interestRate, mc);
        BigDecimal newInterestAmount = PLAccountBalance.add(interestAmount, mc);
        System.out.println("newInterestAmount " + newInterestAmount);
        
        BigDecimal principalAmount = amount.subtract(interestAmount, mc);

        BigDecimal newCreditAmount = crAccountBalance.add(principalAmount, mc);  
        System.out.println("newCreditAmount " + newCreditAmount);

        int drResult = doCreditDebit(drAccountNumber, newDebitAmount, "FOSA", con);
        int crResult = doCreditDebit(crAccountNumber, newCreditAmount, "CONTROL", con);
        int crInterestResult = doCreditDebit(PLAccountNumber, newInterestAmount, "CONTROL", con);
        
        
        
        
        
        int doFtresult = doFTxn(FT, con);
        
        if (drResult == 1 && crResult == 1 && doFtresult == 1 && crInterestResult == 1 ) {
            result = 1;
        }

        return result;
    }

    public static int doFTxn(FinancialTransaction FT, Connection con) {
        int result = 0;
        
        String FTNumber = "FT" + randomGen.generateId(5);

        String SQL = "INSERT INTO `financial_transactions`( "
                + "`FT_NUMBER`, "
                + "`AMOUNT`, "
                + "`DEBIT_ACCOUNT`, "
                + "`CREDIT_ACCOUNT`, "
                + "`TRX_TYPE`, "
                + "`TRX_TYPE_REF` "
                + ") "
                + "VALUES ( "
                + "'" + FTNumber+ "',"
                + "'" + FT.getAmount() + "', "
                + "'" + FT.getDebitAccount() + "', "
                + "'" + FT.getCreditAccount() + "', "
                + "'" + FT.getTransactionTypeID() + "', "
                + "'" + FT.getTransactionTypeRef() + "' "
                + ")";
        int insertResult = SQLInsert(SQL, con);

        if (insertResult == 1) {
            result = 1;
        }
        return result;
    }

    public static int doCreditDebit(String account, BigDecimal amount, String accountType, Connection con) {
        int result = 0;
        String SQL = "";
        if (accountType.equalsIgnoreCase("FOSA")) {
            SQL = "UPDATE fosa_account SET balance=" + amount + " WHERE account_no= '" + account + "' ";
        } else if (accountType.equalsIgnoreCase("CONTROL")) {
            SQL = "UPDATE control_accounts SET amount=" + amount + " WHERE account_no= '" + account + "' ";
        }

        System.out.println("doCreditDebit  " + SQL);
        int updateResult = SQLUpdate(SQL, con);
        if (updateResult == 1) {
            result = 1;
        }
        return result;
    }

    public static int SQLInsert(String SQL, Connection con) {
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

    public static int SQLUpdate(String SQL, Connection con) {
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

}
