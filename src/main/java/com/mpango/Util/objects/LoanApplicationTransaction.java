/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.objects;

import com.mpango.bus.CustomerDTO;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jmulutu
 */
public class LoanApplicationTransaction extends Transaction {

    private int CustomerID;
    private String TransactionRef;
    private String LoanRef; // unique
    private int LoanTypeID;
    private BigDecimal Amount;
    private int RepaymentDuration;

    private BigDecimal interestRate;
    private String interestRateType;

    private int repaymentID;
    private int EMIInstallmentID;
    private String MSISDN;
    private Date repaymentDate;
    private String OtherDetails;
    private String Status;
    private String repaymentType;
    private String debitAccount;
    private String creditAccount;
    
    private CustomerDTO customer;

    public LoanApplicationTransaction() {
    }

    public LoanApplicationTransaction(int CustomerID, String TransactionRef, String LoanRef, int LoanTypeID,
            BigDecimal Amount, int RepaymentDuration, BigDecimal interestRate, String interestRateType) {
        this.CustomerID = CustomerID;
        this.TransactionRef = TransactionRef;
        this.LoanRef = LoanRef;
        this.Amount = Amount;
        this.LoanTypeID = LoanTypeID;
        this.RepaymentDuration = RepaymentDuration;
        interestRate = interestRate;
        interestRateType = interestRateType;
    }

    /**
     * @return the CustomerID
     */
    public int getCustomerID() {
        return CustomerID;
    }

    /**
     * @return the TransactionRef
     */
    public String getTransactionRef() {
        return TransactionRef;
    }

    /**
     * @return the LoanRef
     */
    public String getLoanRef() {
        return LoanRef;
    }

    /**
     * @return the LoanTypeID
     */
    public int getLoanTypeID() {
        return LoanTypeID;
    }

    /**
     * @return the Amount
     */
    public BigDecimal getAmount() {
        return Amount;
    }

    /**
     * @return the RepaymentDuration
     */
    public int getRepaymentDuration() {
        return RepaymentDuration;
    }

    /**
     * @return the interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * @return the interestRateType
     */
    public String getInterestRateType() {
        return interestRateType;
    }

    /**
     * @param CustomerID the CustomerID to set
     */
    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    /**
     * @param TransactionRef the TransactionRef to set
     */
    public void setTransactionRef(String TransactionRef) {
        this.TransactionRef = TransactionRef;
    }

    /**
     * @param LoanRef the LoanRef to set
     */
    public void setLoanRef(String LoanRef) {
        this.LoanRef = LoanRef;
    }

    /**
     * @param LoanTypeID the LoanTypeID to set
     */
    public void setLoanTypeID(int LoanTypeID) {
        this.LoanTypeID = LoanTypeID;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    /**
     * @param RepaymentDuration the RepaymentDuration to set
     */
    public void setRepaymentDuration(int RepaymentDuration) {
        this.RepaymentDuration = RepaymentDuration;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * @param interestRateType the interestRateType to set
     */
    public void setInterestRateType(String interestRateType) {
        this.interestRateType = interestRateType;
    }

    /**
     * @return the repaymentID
     */
    public int getRepaymentID() {
        return repaymentID;
    }

    /**
     * @return the EMIInstallmentID
     */
    public int getEMIInstallmentID() {
        return EMIInstallmentID;
    }

    /**
     * @return the MSISDN
     */
    public String getMSISDN() {
        return MSISDN;
    }

    /**
     * @return the repaymentDate
     */
    public Date getRepaymentDate() {
        return repaymentDate;
    }

    /**
     * @return the OtherDetails
     */
    public String getOtherDetails() {
        return OtherDetails;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @return the repaymentType
     */
    public String getRepaymentType() {
        return repaymentType;
    }

    /**
     * @return the debitAccount
     */
    public String getDebitAccount() {
        return debitAccount;
    }

    /**
     * @return the creditAccount
     */
    public String getCreditAccount() {
        return creditAccount;
    }

    /**
     * @param repaymentID the repaymentID to set
     */
    public void setRepaymentID(int repaymentID) {
        this.repaymentID = repaymentID;
    }

    /**
     * @param EMIInstallmentID the EMIInstallmentID to set
     */
    public void setEMIInstallmentID(int EMIInstallmentID) {
        this.EMIInstallmentID = EMIInstallmentID;
    }

    /**
     * @param MSISDN the MSISDN to set
     */
    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    /**
     * @param repaymentDate the repaymentDate to set
     */
    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    /**
     * @param OtherDetails the OtherDetails to set
     */
    public void setOtherDetails(String OtherDetails) {
        this.OtherDetails = OtherDetails;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @param repaymentType the repaymentType to set
     */
    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    /**
     * @param debitAccount the debitAccount to set
     */
    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    /**
     * @param creditAccount the creditAccount to set
     */
    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    /**
     * @return the customer
     */
    public CustomerDTO getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

}
