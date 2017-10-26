/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.lms;

import com.mpango.dao.idao.I_Transaction;
import com.mpango.bus.TransactionDTO;
import com.mpango.Util.objects.Transaction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author jmulutu
 */
public class TransactionDAO implements I_Transaction {

    @Override
    public TransactionDTO getTransactionByID(int transactionID, Connection con) {
        TransactionDTO transaction = new TransactionDTO();

        String SQL = "select * from tr_log where TRX_ID = " + transactionID + "";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                transaction.setTransactionID(rs.getInt("TRX_ID"));
                transaction.setTransactionRefNum(rs.getString("TRX_REF_NUMBER"));
                transaction.setCustomerID(rs.getInt("CUSTOMER_ID"));
                transaction.setTransactionTypeID(rs.getInt("TRX_TYPE_ID"));
                transaction.setDateLogged(rs.getString("DATE_LOGGED"));
                transaction.setStatus(rs.getString("STATUS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }

        return transaction;
    }

    @Override
    public TransactionDTO getTransactionByTxRef(String trxRef, Connection con) {
        TransactionDTO transaction = new TransactionDTO();

        String SQL = "select * from tr_log where TRX_REF_NUMBER = '" + trxRef + "'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                transaction.setTransactionID(rs.getInt("TRX_ID"));
                transaction.setTransactionRefNum(rs.getString("TRX_REF_NUMBER"));
                transaction.setCustomerID(rs.getInt("CUSTOMER_ID"));
                transaction.setTransactionTypeID(rs.getInt("TRX_TYPE_ID"));
                transaction.setDateLogged(rs.getString("DATE_LOGGED"));
                transaction.setStatus(rs.getString("STATUS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return transaction;
    }

    @Override
    public TransactionDTO createTransaction(Transaction trx, Connection con) {
        TransactionDTO transaction = null;

        String SQL = "INSERT INTO `tr_log`( `TRX_REF_NUMBER`, `CUSTOMER_ID`, `TRX_TYPE_ID`, `DATE_LOGGED` ) "
                + "VALUES ( "
                + "'" + trx.getTransactionRefNum() + "',"
                + "" + trx.getCustomerID() + ","
                + "" + trx.getTransactionTypeID() + ","
                + " NOW()"
                + ")";
        Statement st = null;
        try {
            //st = con.createStatement();
            st = con.prepareStatement(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                transaction = this.getTransactionByTxRef(trx.getTransactionRefNum(), con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return transaction;
    }

    @Override
    public void updateTransactionStatus(int transactionID, String status, Connection con) {

        String SQL = "UPDATE `tr_log` SET status = '" + status + "' WHERE TRX_ID=" + transactionID + "";
        Statement st = null;
        try {
            st = con.prepareStatement(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                //output = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
    }
}
