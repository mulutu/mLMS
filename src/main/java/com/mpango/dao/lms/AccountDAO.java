/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.lms;

import com.mpango.Util.objects.Account;
import com.mpango.dao.idao.I_Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author jmulutu
 */
public class AccountDAO implements I_Account {

    @Override
    public Account getControlAccount(String accountNumber, Connection con) {
        Account account = new Account();

        String SQL = "SELECT * "
                + "FROM control_accounts "
                + "WHERE account_no='" + accountNumber.trim() + "' ";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                account.setAccountID(rs.getInt("ID"));
                account.setAccountName(rs.getString("ACCOUNT_NAME"));
                account.setAccountNumber(rs.getString("ACCOUNT_NO"));
                account.setAccountBalance(rs.getBigDecimal("AMOUNT"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return account;
    }

    @Override
    public Account getAccount(String accountNumber, Connection con) {
        Account account = new Account();

        String SQL = "SELECT * "
                + "FROM fosa_account "
                + "WHERE account_no='" + accountNumber.trim() + "' ";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            while (rs.next()) {
                account.setAccountID(rs.getInt("ID"));
                account.setCustomerID(rs.getInt("CUSTOMER_ID"));
                account.setAccountNumber(rs.getString("ACCOUNT_NO"));
                account.setAccountBalance(rs.getBigDecimal("BALANCE"));
                account.setActive(rs.getInt("ACTIVE"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return account;
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

}
