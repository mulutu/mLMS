/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.bus;

import com.mpango.Util.objects.Account;
import com.mpango.dao.factory.LMSDAOFactory;
import com.mpango.dao.idao.I_Account;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public class AccountBUS {
    
    public static  Account getAccount(String accountNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Account myAccount = factory.get_Account();
        return myAccount.getAccount(accountNumber, con);
    }
    
    public static  Account getControlAccount(String accountNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Account myAccount = factory.get_Account();
        return myAccount.getControlAccount(accountNumber, con);
    }
    
}
