package com.mpango.Util;

import java.math.BigDecimal;

public class Constant {

    public final static String REQUEST = "REQUEST";
    public final static String SOURCE = "SOURCE";
    public final static String QUEUE = "TransactionQueue";
    public final static String RC = "RESPONSECODE";
    public final static String CONN = "CONNECTION";
    public final static String SESSION_ID = "SESSION_ID";
    
    public final static String INVALID_FIELD = "INVALID FIELD";
    
    public final static String POINT = "POINT";
    public final static String AMOUNT = "AMOUNT";
    
    public final static String ACCOUNT_BALANCE = "ACCOUNT_BALANCE";

    public final static String CUSTOMER_MSISDN = "CUSTOMER_MSISDN";
    public final static String CUSTOMER_NAT_ID = "CUSTOMER_NAT_ID";
    public final static String LOAN_REF_NUMBER = "LOAN_REF_NUMBER";
    public final static int INTEREST_RATE = 0;
    public final static int REPAYMENT_PERIOD = 0;
    //public final static BigDecimal LOAN_AMOUNT = new BigDecimal(0);
    //public final static BigDecimal INTEREST_AMOUNT = new BigDecimal(1);
    public final static String LOAN_DUE_DATE = "LOAN DUE DATE"; 
    public final static BigDecimal LOAN_LIMIT = BigDecimal.ZERO; 
    public final static BigDecimal LOAN_BALANCE = BigDecimal.ZERO; 
    public final static String CRB_STATUS = "CRB_STATUS";
    public final static String DISBURSEMENT_CHANNEL = "DISBURSEMENT_CHANNEL";
    public final static String TRX_REF_NO = "TRX_REF_NO";
    
    public final static String ADVERTISE = "ADVERTISEMENT";    

    //Message which send back to client
    public final static String CUSTOMER_NOT_FOUND = "CUSTOMER NOT REGISTERED";
    public final static String DUPLICATE_LOAN = "DUPLICATE LOAN";
    public final static String DISBURSEMENT_SQL_ERROR = "DATABASE ERROR DURING DISBURSEMENT";
    public final static String NO_LOAN_LIMIT = "NO LOAN LIMIT";
    public final static String EXISTING_LOAN = "EXISTING LOAN";
    public final static String LOAN_DOES_NOT_EXIST = "LOAN DOES NOT EXIST";
    
    public final static String ZERO_REPAY_AMOUNT = "ZERO REPAY AMOUNT";
    public final static String LOAN_ALREADY_CLEARED = "LOAN ALREADY CLEARED";
    
    public final static String MID_OR_TID_NOT_FOUND = "MID OR TID NOT FOUND";
    public final static String CARD_NOT_FOUND = "INVALID CARD NUMBER";
    public final static String POSCC_NOT_FOUND = "POSCC NOT FOUND";
    public final static String OTHER_ERROR = "OTHER ERROR";
    public final static String EXPIRE_CARD = "EXPIRE CARD";
    public final static String SUCCESFULL = "TRANSACTION SUCESSFULLY";
    
    public final static String ACTIVATED_CARD = "CARD WAS ACTIVATED";
    public final static String FORWARD_FAIL = "FORWARD_FAIL";
    public final static String NO_ACTIVATED_CARD = "CARD WASN'T ACTIVATED ";
    public final static String NOT_ENOUGH_POINT = "NOT_ENOUGH_POINT";
    public final static String NO_GIFT = "BAN CHUA DU DIEM DE NHAN QUA.";
    public final static String NOT_ENOUGH_MONEY = "NOT_ENOUGH_MONEY";
    public final static String INVOICE_FAIL = "INVOICE_FAIL";
    public final static String RELOAD_FAIL = "RELOAD_FAIL";
    public final static String TRANSACTION_REVERSAL = "REVERSALING_TRANSACTION";

    public final static String ERROR_FLOW = "000000";
    public final static String BALANCE_INQUIRY_PROCESS = "200000";
    public final static String ACCOUNT_BALANCE_INQUIRY_PROCESS = "210000";
    public final static String LOAN_REPAYMENT = "300000";
    public final static String LOAN_APPLICATION = "400000";    
    public final static String REDEEM_PROCESS = "77777";
    
    
    public final static String VOID_REDEEM_PROCESS = "027208";
    public final static String VOID_RELOAD_PROCESS = "027708";
    public final static String RELOAD_PROCESS = "027707";
    public final static String REVERSAL_PROCESS = "020400";
    public final static String REVERSAL_MTI = "0400";
    public final static String SQL_DB = "MYSQL";
}
