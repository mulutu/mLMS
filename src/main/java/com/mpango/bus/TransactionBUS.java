/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.bus;

import com.mpango.Util.objects.Transaction;
import com.mpango.dao.factory.LMSDAOFactory;
import com.mpango.dao.idao.I_Transaction;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public class TransactionBUS {

    public static TransactionDTO getTransactionByID(int trxID, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Transaction myTransaction = factory.get_Transaction();
        return myTransaction.getTransactionByID(trxID, con);
    }

    public static TransactionDTO getTransactionByRef(String trxRef, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Transaction myTransaction = factory.get_Transaction();
        return myTransaction.getTransactionByTxRef(trxRef, con);
    }

    public static TransactionDTO createTransaction(Transaction trn, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Transaction myTransaction = factory.get_Transaction();
        return myTransaction.createTransaction(trn, con);
    }

    public static void updateTransactionStatus(int transactionID, String status, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Transaction myTransaction = factory.get_Transaction();
        myTransaction.updateTransactionStatus(transactionID, status, con);
    }
}