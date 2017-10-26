package com.mpango.dao.factory;

import com.mpango.dao.idao.I_Account;
import com.mpango.dao.idao.I_Customer;
import com.mpango.dao.idao.I_Loan;
import com.mpango.dao.idao.I_Menu;
import com.mpango.dao.idao.I_Transaction;
import com.mpango.dao.lms.AccountDAO;
import com.mpango.dao.lms.CustomerDAO;
import com.mpango.dao.lms.LoanDAO;
import com.mpango.dao.lms.MenuDAO;
import com.mpango.dao.lms.TransactionDAO;

/**
 * Concrete SQL database object of LMS DAO Factory.
 *
 * @author HUNGPT
 * @see LMSDAOFactory
 */
public class LMSSqlDAO extends LMSDAOFactory {
    
    @Override
    public I_Account get_Account() {
        return new AccountDAO();
    }
    
    @Override
    public I_Menu get_Menu() {
        return new MenuDAO();
    }

    @Override
    public I_Loan get_Loan() {
        return new LoanDAO();
    }

    @Override
    public I_Transaction get_Transaction() {
        return new TransactionDAO();
    }
    
    @Override
    public I_Customer get_Customer() {
        return new CustomerDAO();
    }
    
}
