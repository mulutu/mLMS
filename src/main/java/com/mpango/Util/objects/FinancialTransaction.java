/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.Util.objects;

import java.math.BigDecimal;

/**
 *
 * @author jmulutu
 */
public class FinancialTransaction extends Transaction{
    private String DebitAccount;
    private String CreditAccount;
    private BigDecimal Amount;
    private String transactionTypeRef;
    private BigDecimal interestRate;
    
    public FinancialTransaction(){
        super();
    }

    /**
     * @return the DebitAccount
     */
    public String getDebitAccount() {
        return DebitAccount;
    }

    /**
     * @return the CreditAccount
     */
    public String getCreditAccount() {
        return CreditAccount;
    }

    /**
     * @return the Amount
     */
    public BigDecimal getAmount() {
        return Amount;
    }

    /**
     * @param DebitAccount the DebitAccount to set
     */
    public void setDebitAccount(String DebitAccount) {
        this.DebitAccount = DebitAccount;
    }

    /**
     * @param CreditAccount the CreditAccount to set
     */
    public void setCreditAccount(String CreditAccount) {
        this.CreditAccount = CreditAccount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the transactionTypeRef
     */
    public String getTransactionTypeRef() {
        return transactionTypeRef;
    }

    /**
     * @param transactionTypeRef the transactionTypeRef to set
     */
    public void setTransactionTypeRef(String transactionTypeRef) {
        this.transactionTypeRef = transactionTypeRef;
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
