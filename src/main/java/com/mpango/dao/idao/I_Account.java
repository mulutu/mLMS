/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.dao.idao;

import com.mpango.Util.objects.Account;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public interface I_Account {
    public Account getAccount(String accountNumber, Connection con);
    
    public Account getControlAccount(String accountNumber, Connection con);
}
