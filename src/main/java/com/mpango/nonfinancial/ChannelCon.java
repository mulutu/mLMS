/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.nonfinancial;

import com.mpango.bus.CustomerBUS;
import com.mpango.bus.CustomerDTO;
import com.mpango.bus.MenuBUS;
import com.mpango.bus.MenuDTO;
import com.mpango.dao.lms.DBConnectionPool;
import com.mpango.dao.lms.DataProvider;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public class ChannelCon implements Runnable {

    static Logger logger = Logger.getLogger(ChannelCon.class.getName());

    Socket incoming;
    ServerSocket s;

    public ChannelCon(Socket incoming, ServerSocket s) {
        this.incoming = incoming;
        this.s = s;
    }

    @Override
    public void run() {
        process();
    }

    public String processRequest(String requestData) {
        String result = null;
        String delims = "[|]";
        String[] serviceRequest = requestData.split(delims);
        String requestType = serviceRequest[0];
        logger.info("Processing login process---> : " + requestData);
        if (requestType.equals("ISCUST")) {
            result = isCustomer(requestData);
        } else if (requestType.equals("CONFIG")) {
            result = getConfig(requestData);
        } else if (requestType.equals("LOGIN")) {
            result = login(requestData);
        }
        return result;
    }

    public String login(String requestData) {
        String result = null;
        Connection con = DataProvider.getConnection();

        String delims = "[|]";
        String[] serviceRequest = requestData.split(delims);
        String customerMSISDN = serviceRequest[1];

        CustomerDTO customer = CustomerBUS.getCustomerByMSISDN(customerMSISDN, con);
        String fname = customer.getFirstName();
        String mname = customer.getMiddleName();
        String lname = customer.getLastName();
        BigDecimal loanLimit = customer.getLoanLimit();
        String IDNumber = customer.getID_NUMBER();
        int customerID = customer.getCustomerID();
        Date joinDate = customer.getDateJoin();
        String gender = customer.getGender();
        String email = customer.getEmail();
        String activeLoanRef = CustomerBUS.getCustomerActiveLoan(IDNumber, con);
        result = "00|SUCCESS|" + customerID + "|" + fname + "|" + mname + "|" + lname + "|" + loanLimit + "|" + IDNumber + "|" + joinDate + "|" + gender + "|" + email + "|" + activeLoanRef;

        if (con != null) {
            DbUtils.closeQuietly(con);
        }

        return result;
    }

    public String isCustomer(String requestData) {
        String result = null;
        String delims = "[|]";
        String[] serviceRequest = requestData.split(delims);
        CustomerDTO customer = null;
        String userMSISDN = serviceRequest[1];

        Connection con = DataProvider.getConnection();
        customer = CustomerBUS.getCustomerByMSISDN(userMSISDN, con);

        if (customer.getMSISDN().equalsIgnoreCase(userMSISDN)) {
            String fname = customer.getFirstName();
            String mname = customer.getMiddleName();
            String lname = customer.getLastName();
            BigDecimal loanLimit = customer.getLoanLimit();
            String IDNumber = customer.getID_NUMBER();
            int customerID = customer.getCustomerID();
            Date joinDate = customer.getDateJoin();
            String gender = customer.getGender();
            String email = customer.getEmail();
            String PIN = customer.getPIN();
            String activeLoanRef = CustomerBUS.getCustomerActiveLoan(IDNumber, con);
            String FOSAAccountNumber = customer.getFOSAAccountNumber();
            result = "00|SUCCESS|" + customerID + "|" + fname + "|" + mname + "|" + lname + "|" + loanLimit + "|" 
                    + IDNumber + "|" + joinDate + "|" + gender + "|" + email + "|" + activeLoanRef + "|" + PIN + "|" + FOSAAccountNumber;
        } else {
            logger.info("isCustomer >>> failed");
            result = "01|FAILED";
        }
        if (con != null) {
            DbUtils.closeQuietly(con);
        }
        return result;
    }

    public String getConfig(String requestData) {
        String result = null;
        String delims = "[|]";
        String[] serviceRequest = requestData.split(delims);
        String configType = serviceRequest[1];
        if (configType.equals("MENUS")) {
            result = buildMainMenu();
        }
        return result;
    }

    public String buildMainMenu() {
        //Connection con = DataProvider.getConnection();
        List<MenuDTO> menus = null;
        String result2 = null;
        String responseCode = "01";
        Connection con = DataProvider.getConnection();
        menus = MenuBUS.getMenus(con);
        if (!menus.isEmpty()) {
            responseCode = "00";
            result2 = responseCode
                    + "|menu.level.127=Welcome. Please enter your PIN.\n"
                    + "|menu.level.126=Wrong PIN. Please enter your PIN again.\n"
                    + "|menu.level.125=You are not registered.\n";
            for (int i = 0; i < menus.size(); i++) {
                MenuDTO menu = menus.get(i);
                Byte serviceCode = menu.getServiceCode();
                String menuName = menu.getName();
                int menuID = menu.getID();
                result2 = result2 + "|menu.level." + serviceCode + "=" + menuName + getMenuString(menuID);
            }
        }
        if (con != null) {
            DbUtils.closeQuietly(con);
        }
        return result2;
    }

    public String getMenuString(int menuID) {
        logger.info("--- starting getMenuString ---> : ");
        List<MenuDTO> menus = null;
        String result = "";
        Connection con = DataProvider.getConnection();
        menus = MenuBUS.getMenusByParentID(menuID, con);
        for (int i = 0; i < menus.size(); i++) {
            MenuDTO menu = menus.get(i);
            int positionID = menu.getPositionID();
            String menuName = menu.getName();
            result = result + "\n" + positionID + ". " + menuName;
        }
        if (con != null) {
            DbUtils.closeQuietly(con);
        }
        result = result + "\n999.Back\n000.Exit";
        return result;
    }

    public void process() {
        try {
            DataInputStream din = new DataInputStream(incoming.getInputStream());
            DataOutputStream dout = new DataOutputStream(incoming.getOutputStream());
            String inp = null;
            boolean isDone = true;

            while (isDone && ((inp = din.readUTF()) != null)) {
                if (inp.trim().equals("BYE")) {
                    isDone = false;
                    s.close();
                }
                String requestdata = inp.trim();
                String result = processRequest(requestdata);
                dout.writeUTF(result);
                dout.flush();
                isDone = false;
            }
        } catch (IOException e) { // TODO Auto-generated catch block
            try {
                s.close();
            } catch (IOException e1) { // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
