/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util;

import com.mpango.bus.LoanDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jmulutu
 */
public class UtilityFunctions {
    
    public static int sendToMpesa( LoanDTO loanApplication, Connection con) {

        // return 2
        return 1;
    }
    
    public static int logUpdateReceivedISO(String loanRefNumber, String responseISO, Connection con) {
        int result = 0;
        String SQL = "UPDATE `iso_received` SET RESPONSE_ISO = '" + responseISO + "' WHERE LOAN_REF='" + loanRefNumber + "'";
        Statement st;
        try {
            st = con.createStatement();
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static int logReceivedISO(String loanRefNumber, String receivedISO, Connection con) {
        int result = 0;
        String SQL = "INSERT INTO `iso_received`( `DATE`, `LOAN_REF`, `RECEIVED_ISO` ) VALUES ( NOW(), '" + loanRefNumber + "', '" + receivedISO + "' )";
        Statement st;
        try {
            st = con.createStatement();
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getSQLState().startsWith("23")) {
                result = 2;
            } else {
                result = 3;
            }
        }
        return result;
    }
    
}
