/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.bus;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jmulutu
 */
public class LoanDTO {

    private int loanID;
    private int loanTerms;

    private int customerID;
    private String customerMSISDN;

    private String trxRefNumber;
    private String loanRef;
    private int loanTypeID;
    private String loanType;
    private String requestDate;
    private String dateDisbursed;
    private String dueDate;
    private BigDecimal amount;
    private int loanTerm;
    private BigDecimal interestRate;
    private BigDecimal principal;
    private String interestRateType;
    private BigDecimal loanBalance;
    private BigDecimal totalPenalties;
    private BigDecimal totalRepayments;
    private String status;
    private int currentEMIID;
    private int currentEMIInstallmentNumber;

    private BigDecimal emiAmount;
    private BigDecimal emiAmountReceived;
    private BigDecimal emiTotalReceivable;
    private BigDecimal emiOutstandingAmount;
    private BigDecimal emiPenalty;
    private Date emiDueDate;
    private String emiSchedule;
    
    private String repaymentAccount;
    private String controlAccount;

    public LoanDTO() {
        loanID = 0;
        loanTerms = 0;
        customerID = 0;
        customerMSISDN = "";
        trxRefNumber = "";
        loanRef = "";
        loanTypeID = 0;
        loanType = "";
        requestDate = "";
        dateDisbursed = "";
        amount = null;
        loanTerm = 0;
        interestRate = null;
        principal = null;
        dueDate = "";
        interestRateType = "";
        loanBalance = null;
        totalPenalties = null;
        totalRepayments = null;
        status = "";
        currentEMIID = 1;
        currentEMIInstallmentNumber = 1;
        emiAmount = null;
        emiAmountReceived = null;
        emiTotalReceivable = null;
        emiOutstandingAmount = null;
        emiPenalty = null;
        emiDueDate = null;
        emiSchedule = "";
    }

    /**
     * @return the loanID
     */
    public int getLoanID() {
        return loanID;
    }

    /**
     * @return the loanTerms
     */
    public int getLoanTerms() {
        return loanTerms;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return the customerMSISDN
     */
    public String getCustomerMSISDN() {
        return customerMSISDN;
    }

    /**
     * @return the trxRefNumber
     */
    public String getTrxRefNumber() {
        return trxRefNumber;
    }

    /**
     * @return the loanRef
     */
    public String getLoanRef() {
        return loanRef;
    }

    /**
     * @return the loanTypeID
     */
    public int getLoanTypeID() {
        return loanTypeID;
    }

    /**
     * @return the loanType
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * @return the requestDate
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @return the dateDisbursed
     */
    public String getDateDisbursed() {
        return dateDisbursed;
    }

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return the loanTerm
     */
    public int getLoanTerm() {
        return loanTerm;
    }

    /**
     * @return the interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * @return the principal
     */
    public BigDecimal getPrincipal() {
        return principal;
    }

    /**
     * @return the interestRateType
     */
    public String getInterestRateType() {
        return interestRateType;
    }

    /**
     * @return the loanBalance
     */
    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    /**
     * @return the totalPenalties
     */
    public BigDecimal getTotalPenalties() {
        return totalPenalties;
    }

    /**
     * @return the totalRepayments
     */
    public BigDecimal getTotalRepayments() {
        return totalRepayments;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the currentEMIID
     */
    public int getCurrentEMIID() {
        return currentEMIID;
    }

    /**
     * @return the currentEMIInstallmentNumber
     */
    public int getCurrentEMIInstallmentNumber() {
        return currentEMIInstallmentNumber;
    }

    /**
     * @return the emiAmount
     */
    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    /**
     * @return the emiAmountReceived
     */
    public BigDecimal getEmiAmountReceived() {
        return emiAmountReceived;
    }

    /**
     * @return the emiTotalReceivable
     */
    public BigDecimal getEmiTotalReceivable() {
        return emiTotalReceivable;
    }

    /**
     * @return the emiOutstandingAmount
     */
    public BigDecimal getEmiOutstandingAmount() {
        return emiOutstandingAmount;
    }

    /**
     * @return the emiPenalty
     */
    public BigDecimal getEmiPenalty() {
        return emiPenalty;
    }

    /**
     * @return the emiDueDate
     */
    public Date getEmiDueDate() {
        return emiDueDate;
    }

    /**
     * @return the emiSchedule
     */
    public String getEmiSchedule() {
        return emiSchedule;
    }

    /**
     * @param loanID the loanID to set
     */
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    /**
     * @param loanTerms the loanTerms to set
     */
    public void setLoanTerms(int loanTerms) {
        this.loanTerms = loanTerms;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @param customerMSISDN the customerMSISDN to set
     */
    public void setCustomerMSISDN(String customerMSISDN) {
        this.customerMSISDN = customerMSISDN;
    }

    /**
     * @param trxRefNumber the trxRefNumber to set
     */
    public void setTrxRefNumber(String trxRefNumber) {
        this.trxRefNumber = trxRefNumber;
    }

    /**
     * @param loanRef the loanRef to set
     */
    public void setLoanRef(String loanRef) {
        this.loanRef = loanRef;
    }

    /**
     * @param loanTypeID the loanTypeID to set
     */
    public void setLoanTypeID(int loanTypeID) {
        this.loanTypeID = loanTypeID;
    }

    /**
     * @param loanType the loanType to set
     */
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @param dateDisbursed the dateDisbursed to set
     */
    public void setDateDisbursed(String dateDisbursed) {
        this.dateDisbursed = dateDisbursed;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @param loanTerm the loanTerm to set
     */
    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    /**
     * @param interestRateType the interestRateType to set
     */
    public void setInterestRateType(String interestRateType) {
        this.interestRateType = interestRateType;
    }

    /**
     * @param loanBalance the loanBalance to set
     */
    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    /**
     * @param totalPenalties the totalPenalties to set
     */
    public void setTotalPenalties(BigDecimal totalPenalties) {
        this.totalPenalties = totalPenalties;
    }

    /**
     * @param totalRepayments the totalRepayments to set
     */
    public void setTotalRepayments(BigDecimal totalRepayments) {
        this.totalRepayments = totalRepayments;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param currentEMIID the currentEMIID to set
     */
    public void setCurrentEMIID(int currentEMIID) {
        this.currentEMIID = currentEMIID;
    }

    /**
     * @param currentEMIInstallmentNumber the currentEMIInstallmentNumber to set
     */
    public void setCurrentEMIInstallmentNumber(int currentEMIInstallmentNumber) {
        this.currentEMIInstallmentNumber = currentEMIInstallmentNumber;
    }

    /**
     * @param emiAmount the emiAmount to set
     */
    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }

    /**
     * @param emiAmountReceived the emiAmountReceived to set
     */
    public void setEmiAmountReceived(BigDecimal emiAmountReceived) {
        this.emiAmountReceived = emiAmountReceived;
    }

    /**
     * @param emiTotalReceivable the emiTotalReceivable to set
     */
    public void setEmiTotalReceivable(BigDecimal emiTotalReceivable) {
        this.emiTotalReceivable = emiTotalReceivable;
    }

    /**
     * @param emiOutstandingAmount the emiOutstandingAmount to set
     */
    public void setEmiOutstandingAmount(BigDecimal emiOutstandingAmount) {
        this.emiOutstandingAmount = emiOutstandingAmount;
    }

    /**
     * @param emiPenalty the emiPenalty to set
     */
    public void setEmiPenalty(BigDecimal emiPenalty) {
        this.emiPenalty = emiPenalty;
    }

    /**
     * @param emiDueDate the emiDueDate to set
     */
    public void setEmiDueDate(Date emiDueDate) {
        this.emiDueDate = emiDueDate;
    }

    /**
     * @param emiSchedule the emiSchedule to set
     */
    public void setEmiSchedule(String emiSchedule) {
        this.emiSchedule = emiSchedule;
    }

    /**
     * @return the repaymentAccount
     */
    public String getRepaymentAccount() {
        return repaymentAccount;
    }

    /**
     * @return the controlAccount
     */
    public String getControlAccount() {
        return controlAccount;
    }

    /**
     * @param repaymentAccount the repaymentAccount to set
     */
    public void setRepaymentAccount(String repaymentAccount) {
        this.repaymentAccount = repaymentAccount;
    }

    /**
     * @param controlAccount the controlAccount to set
     */
    public void setControlAccount(String controlAccount) {
        this.controlAccount = controlAccount;
    }

    
}
