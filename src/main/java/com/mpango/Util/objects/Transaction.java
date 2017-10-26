/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.objects;

/**
 *
 * @author jmulutu
 */
public class Transaction {

    private int transactionID;
    private String transactionRefNum;
    private int customerID;
    private int transactionTypeID;
    private String transactionType;
    private String dateLogged;
    private String dateModified;
    private String status;
    
    public Transaction(){}

    public Transaction(String transactionRefNum, int customerID, int transactionTypeID, String status) {
        this.transactionRefNum = transactionRefNum;
        this.customerID = customerID;
        this.transactionTypeID = transactionTypeID;
        this.status = status;
    }

    /**
     * @return the transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }

    /**
     * @return the transactionRefNum
     */
    public String getTransactionRefNum() {
        return transactionRefNum;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return the transactionTypeID
     */
    public int getTransactionTypeID() {
        return transactionTypeID;
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @return the dateLogged
     */
    public String getDateLogged() {
        return dateLogged;
    }

    /**
     * @return the dateModified
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param transactionID the transactionID to set
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * @param transactionRefNum the transactionRefNum to set
     */
    public void setTransactionRefNum(String transactionRefNum) {
        this.transactionRefNum = transactionRefNum;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @param transactionTypeID the transactionTypeID to set
     */
    public void setTransactionTypeID(int transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @param dateLogged the dateLogged to set
     */
    public void setDateLogged(String dateLogged) {
        this.dateLogged = dateLogged;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
