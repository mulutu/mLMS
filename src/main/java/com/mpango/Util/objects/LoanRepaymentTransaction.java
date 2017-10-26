/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.Util.objects;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author jmulutu
 */
public class LoanRepaymentTransaction extends Transaction {
    
    private int repaymentID;
    private int EMIInstallmentID;
    private String MSISDN;
    private String LoanRef;
    private BigDecimal RepaymentAmount;
    private Date repaymentDate;
    private String OtherDetails;
    private String Status;
    private String repaymentType;
    private String debitAccount;
    private String creditAccount;
    private BigDecimal interestRate;
    
    public LoanRepaymentTransaction(){}

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
     * @return the LoanRef
     */
    public String getLoanRef() {
        return LoanRef;
    }

    /**
     * @return the RepaymentAmount
     */
    public BigDecimal getRepaymentAmount() {
        return RepaymentAmount;
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
     * @param LoanRef the LoanRef to set
     */
    public void setLoanRef(String LoanRef) {
        this.LoanRef = LoanRef;
    }

    /**
     * @param RepaymentAmount the RepaymentAmount to set
     */
    public void setRepaymentAmount(BigDecimal RepaymentAmount) {
        this.RepaymentAmount = RepaymentAmount;
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
     * @return the creditAccount
     */
    public String getCreditAccount() {
        return creditAccount;
    }


    /**
     * @param creditAccount the creditAccount to set
     */
    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    /**
     * @return the debitAccount
     */
    public String getDebitAccount() {
        return debitAccount;
    }

    /**
     * @param debitAccount the debitAccount to set
     */
    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    /**
     * @return the interestRate
     */
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

   
}
