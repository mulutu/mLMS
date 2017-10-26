package com.mpango.dao.factory;

import com.mpango.Util.Constant;
import com.mpango.Util.db.LMSConfig;
import com.mpango.dao.idao.I_Account;
import com.mpango.dao.idao.I_Customer;
import com.mpango.dao.idao.I_Loan;
import com.mpango.dao.idao.I_Menu;
import com.mpango.dao.idao.I_Transaction;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

/**
 *
 * @author HUNGPT General class for all database.
 */
public abstract class LMSDAOFactory { 
    
    public abstract I_Account get_Account();
    
    public abstract I_Menu get_Menu();

    public abstract I_Loan get_Loan();

    public abstract I_Transaction get_Transaction();

    // Get Customer interface stub.
    public abstract I_Customer get_Customer();

    /**
     * Get instance of class indicate for specific database..
     *
     * @return instance of class indicate for specific database.
     */
    public static LMSDAOFactory getInstances() {
        LMSConfig cfg;
        try {
            cfg = (LMSConfig) NameRegistrar.get("MyConfigDB");
            if (cfg.getTypeOfDatabase().equals(Constant.SQL_DB)) {
                return new LMSSqlDAO();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //default SQL server
        return new LMSSqlDAO();
    }
}
