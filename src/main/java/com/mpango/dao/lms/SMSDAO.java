/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.dao.lms;

import com.mpango.Util.objects.SMSMessage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmulutu
 */
public class SMSDAO {

    int SMSTypeID;
    String recepientMSISDN;
    String SMSMessage;

    public SMSDAO() {
    }   
    
    public static int updateSMSsent(SMSMessage sms, Connection con) {
        int result = 0;
        String SQL = "UPDATE sms_messages " + "SET status='SENT', date_sent=NOW() " + "WHERE ID = " + sms.getSmsID() + "";
        int updateResult;
        updateResult = SQLUpdate(SQL, con);
        if (updateResult == 1) {
            result = 1;
        }
        return result;
    }

    public static List<SMSMessage> getAllPendinSMS(Connection con) {
        List<SMSMessage> smslist = new ArrayList<>();
        String SQL = "SELECT * " + "FROM sms_messages " + "WHERE STATUS='PENDING' ";
        Statement st;        
        try {
            st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                SMSMessage sms = new SMSMessage();                
                sms.setSmsID(rs.getInt("ID"));
                sms.setSmsTypeID(rs.getInt("SMS_TYPE_ID"));
                sms.setReceiverMSISDN(rs.getString("RECEIVER_MSISDN"));
                sms.setSmsMessage(rs.getString("MESSAGE"));
                sms.setDateCreated(rs.getDate("DATE_CREATED"));
                sms.setSmsStatus(rs.getString("STATUS"));
                sms.setDateSent(rs.getDate("DATE_SENT"));
                smslist.add(sms);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return smslist;
    }

    public static int createSMS(int SMSTypeID, String recepientMSISDN, String SMSMessage, Connection con) {
        int result = 0;
        String SQL = "INSERT INTO `sms_messages`( `SMS_TYPE_ID`, `RECEIVER_MSISDN`, `MESSAGE` ) "
                + "VALUES ( " + SMSTypeID + ", '" + recepientMSISDN + "', '" + SMSMessage + "' )";
        Statement st;
        try {
            st = con.prepareStatement(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //this.scheduleSMS(); INSERT SMSDAO into SMS_OUBOX TABLE
        return result;
    }
    
     public static int SQLInsert(String SQL, Connection con) {
        int result = 0;
        Statement st;
        try {
            st = con.prepareCall(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int SQLUpdate(String SQL, Connection con) {
        int result = 0;
        Statement sttm;
        try {
            sttm = con.prepareStatement(SQL);
            int rowsUpdated = sttm.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
            sttm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
