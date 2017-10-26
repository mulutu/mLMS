/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.Util.objects;

import java.util.Date;

/**
 *
 * @author jmulutu
 */
public class SMSMessage {
    private int smsID;
    private int smsTypeID;
    private String smsTypeDesc;
    private String receiverMSISDN;
    private String smsMessage;
    private Date dateCreated;
    private Date dateSent;
    private String smsStatus;
    
    public SMSMessage(){
    }

    /**
     * @return the smsID
     */
    public int getSmsID() {
        return smsID;
    }

    /**
     * @return the smsTypeID
     */
    public int getSmsTypeID() {
        return smsTypeID;
    }

    /**
     * @return the smsTypeDesc
     */
    public String getSmsTypeDesc() {
        return smsTypeDesc;
    }

    /**
     * @return the receiverMSISDN
     */
    public String getReceiverMSISDN() {
        return receiverMSISDN;
    }

    /**
     * @return the smsMessage
     */
    public String getSmsMessage() {
        return smsMessage;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return the dateSent
     */
    public Date getDateSent() {
        return dateSent;
    }

    /**
     * @return the smsStatus
     */
    public String getSmsStatus() {
        return smsStatus;
    }

    /**
     * @param smsID the smsID to set
     */
    public void setSmsID(int smsID) {
        this.smsID = smsID;
    }

    /**
     * @param smsTypeID the smsTypeID to set
     */
    public void setSmsTypeID(int smsTypeID) {
        this.smsTypeID = smsTypeID;
    }

    /**
     * @param smsTypeDesc the smsTypeDesc to set
     */
    public void setSmsTypeDesc(String smsTypeDesc) {
        this.smsTypeDesc = smsTypeDesc;
    }

    /**
     * @param receiverMSISDN the receiverMSISDN to set
     */
    public void setReceiverMSISDN(String receiverMSISDN) {
        this.receiverMSISDN = receiverMSISDN;
    }

    /**
     * @param smsMessage the smsMessage to set
     */
    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @param dateSent the dateSent to set
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * @param smsStatus the smsStatus to set
     */
    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }
    
    
}
