package com.mpango.bus;

import com.mpango.dao.factory.LMSDAOFactory;
import com.mpango.dao.idao.I_Customer;
import java.math.BigDecimal;
import java.sql.Connection;

/**
 *
 * @author HUNGPT Customer Bus Service for Customer DAO .
 */
public class CustomerBUS {

    public static CustomerDTO getCustomerByID(String IDNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.getCustomerByID(IDNumber, con);
    }
    
    public static CustomerDTO getCustomerByMSISDN(String MSISDN, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.getCustomerByMSISDN(MSISDN, con);
    }

    public static BigDecimal getLoanLimit(String IDNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.getLoanLimit(IDNumber, con);
    }

    public static int isRegisteredCustomer(String IDNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.isRegisteredCustomer(IDNumber, con);
    }
    
    public static int hasExistingLoan(String IDNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.hasExistingLoan(IDNumber, con);
    }
    
    
    public static String getCustomerActiveLoan(String IDNumber, Connection con) {
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.getCustomerActiveLoan(IDNumber, con);
    }
    
    
    
    public static int hasExistingLoanApplication(String IDNumber, Connection con){
        LMSDAOFactory factory = LMSDAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.hasExistingLoanApplication(IDNumber, con);
    }
}
