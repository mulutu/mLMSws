/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jmulutu
 */
@XmlRootElement(name = "LoanBalanceResponse")
@XmlType(propOrder = {"loanRef", "loanBalance", "dueDate"})
public class LoanBalanceResponse {

    private String loanRef;
    private String loanBalance;
    private String dueDate;

    public LoanBalanceResponse() {
    }

    public LoanBalanceResponse(String loanRef, String loanBalance, String dueDate) {
        this.loanRef = loanRef;
        this.loanBalance = loanBalance;
        this.dueDate = dueDate;
    }

    /**
     * @return the loanRef
     */
    @XmlElement(name = "LoanNumber")
    public String getLoanRef() {
        return loanRef;
    }

    /**
     * @return the loanBalance
     */
    @XmlElement(name = "LoanBalance")
    public String getLoanBalance() {
        return loanBalance;
    }

    /**
     * @return the dueDate
     */
    @XmlElement(name = "DueDate")
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param loanRef the loanRef to set
     */
    public void setLoanRef(String loanRef) {
        this.loanRef = loanRef;
    }

    /**
     * @param loanBalance the loanBalance to set
     */
    public void setLoanBalance(String loanBalance) {
        this.loanBalance = loanBalance;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
