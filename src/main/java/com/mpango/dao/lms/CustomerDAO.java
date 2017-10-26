package com.mpango.dao.lms;

import com.mpango.bus.CustomerDTO;
import com.mpango.dao.idao.I_Customer;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;

/**
 * JPOS Customer Data Access Player
 *
 * @author HUNGPT
 *
 */
public class CustomerDAO implements I_Customer {

    @Override
    public CustomerDTO getCustomerByMSISDN(String MSISDN, Connection con) {
        CustomerDTO customer = new CustomerDTO();
        String SQL = "select * from customers, fosa_account where customers.MSISDN = '" + MSISDN + "' AND customers.customer_id=fosa_account.customer_id";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                customer.setCustomerID(rs.getInt("CUSTOMER_ID"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setMiddleName(rs.getString("MIDDLE_NAME"));
                customer.setLastName(rs.getString("SURNAME"));
                customer.setLoanLimit(rs.getBigDecimal("LOAN_LIMIT"));
                customer.setMSISDN(rs.getString("MSISDN"));
                customer.setID_NUMBER(rs.getString("ID_NUMBER"));
                customer.setPIN(rs.getString("PASSWORD"));
                customer.setFOSAAccountNumber(rs.getString("ACCOUNT_NO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }

        return customer;
    }

    @Override
    public CustomerDTO getCustomerByID(String IDNumber, Connection con) {
        CustomerDTO customer = new CustomerDTO();
        String SQL = "select * from customers where ID_NUMBER = '" + IDNumber + "'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                customer.setCustomerID(rs.getInt("CUSTOMER_ID"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setMiddleName(rs.getString("MIDDLE_NAME"));
                customer.setLastName(rs.getString("SURNAME"));
                //customer.setEmail(rs.getString("FIRST_NAME"));
                //customer.setDateJoin(rs.getDate("MIDDLE_NAME"));
                customer.setLastName(rs.getString("SURNAME"));
                customer.setLoanLimit(rs.getBigDecimal("LOAN_LIMIT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }

        return customer;
    }

    @Override
    public BigDecimal getLoanLimit(String IDNumber, Connection con) {
        BigDecimal loanLimit = null;
        String SQL = "SELECT loan_limit FROM customers WHERE id_number = " + IDNumber;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                loanLimit = (BigDecimal) rs.getBigDecimal("loan_limit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return loanLimit;
    }

    @Override
    public int isRegisteredCustomer(String IDNumber, Connection con) {
        int status = 0;
        String SQL = "SELECT * FROM customers WHERE ID_NUMBER = '" + IDNumber + "'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(SQL);
            if (!rs.isBeforeFirst()) {
                status = 0;
            } else {
                status = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return status;
    }

    @Override
    public int hasExistingLoan(String IDNumber, Connection con) {
        int status = 0;
        String SQL = "  SELECT * \n"
                + "FROM loan_disbursements as d, customers as c \n"
                + "WHERE  c.customer_id = d.customer_id \n"
                + "AND c.id_number = '" + IDNumber + "' "
                + "AND d.STATUS='ACTIVE'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            if (!rs.isBeforeFirst()) {
                status = 0;
            } else {
                status = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return status;
    }

    @Override
    public String getCustomerActiveLoan(String IDNumber, Connection con) {
        String loanRef = "";
        String SQL = "  SELECT * \n"
                + "FROM loan_disbursements as d, customers as c \n"
                + "WHERE  c.customer_id = d.customer_id \n"
                + "AND c.id_number = '" + IDNumber + "' "
                + "AND d.STATUS='ACTIVE'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            if (rs.next()) {
                loanRef = rs.getString("LOAN_REF");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return loanRef;
    }

    @Override
    public int hasExistingLoanApplication(String IDNumber, Connection con) {
        int status = 0;
        String SQL = "  SELECT * \n"
                + "FROM loan_applications as la, customers as c \n"
                + "WHERE  c.customer_id = la.customer_id \n"
                + "AND c.id_number = '" + IDNumber + "' "
                + "AND la.STATUS='PENDING'";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(SQL);
            rs = st.executeQuery(SQL);
            if (!rs.isBeforeFirst()) {
                status = 0;
            } else {
                status = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(st);
            //DbUtils.closeQuietly(con);
        }
        return status;
    }
}
