package com.mpango.dao.idao;

import com.mpango.bus.CustomerDTO;
import java.math.BigDecimal;
import java.sql.Connection;

/**
 *
 * @author HUNGPT Customer interface stub.
 */
public interface I_Customer {

    public CustomerDTO getCustomerByID(String IDNumber, Connection con);

    public CustomerDTO getCustomerByMSISDN(String MSISDN, Connection con);

    public BigDecimal getLoanLimit(String IDNumber, Connection con);

    public int isRegisteredCustomer(String IDNumber, Connection con);

    public int hasExistingLoan(String IDNumber, Connection con);
    
    public String getCustomerActiveLoan(String IDNumber, Connection con);
    
    public int hasExistingLoanApplication(String IDNumber, Connection con);

}
