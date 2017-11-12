/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.bus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author jmulutu
 */
public class LoanDTO {

    private int loanID;
    private String loanRef;
    private String dateDisbursed;
    private String dueDate;
    private BigDecimal amount;
    private int repaymentDuration;
    private int interestRate;
    private BigDecimal loanBalance;

    public LoanDTO() {
        loanID = 0;
        loanRef = "";
        dateDisbursed = "";
        dueDate = "";
        amount = null;
        repaymentDuration = 0;
        interestRate = 0;
        loanBalance = null;
    }

    /**
     * @return the loanID
     */
    public int getLoanID() {
        return loanID;
    }

    /**
     * @param loanID the loanID to set
     */
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    /**
     * @return the loanRef
     */
    public String getLoanRef() {
        return loanRef;
    }

    /**
     * @param loanRef the loanRef to set
     */
    public void setLoanRef(String loanRef) {
        this.loanRef = loanRef;
    }

    /**
     * @return the dateDisbursed
     */
    public String getDateDisbursed() {
        return dateDisbursed;
    }

    /**
     * @param dateDisbursed the dateDisbursed to set
     */
    public void setDateDisbursed(String dateDisbursed) {
        this.dateDisbursed = dateDisbursed;
    }

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the repaymentDuration
     */
    public int getRepaymentDuration() {
        return repaymentDuration;
    }

    /**
     * @param repaymentDuration the repaymentDuration to set
     */
    public void setRepaymentDuration(int repaymentDuration) {
        this.repaymentDuration = repaymentDuration;
    }

    /**
     * @return the interestRate
     */
    public int getInterestRate() {
        return interestRate;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * @return the loanBalance
     */
    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    /**
     * @param loanBalance the loanBalance to set
     */
    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

}
