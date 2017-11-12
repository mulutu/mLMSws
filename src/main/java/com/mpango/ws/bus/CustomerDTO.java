/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.bus;

import java.math.BigDecimal;

/**
 *
 * @author jmulutu
 */
public class CustomerDTO {
    private  int CustomerID;
    private  String firstName;
    private  String middleName;
    private  String surname;
    private  String nationalID;
    private  String msisdn;
    private  String gender;
    private  int CRBStatus;
    private BigDecimal loanLimit;

    public CustomerDTO() {
        CustomerID = 0;
        firstName = "";
        middleName = "";
        surname = "";
        nationalID = "";
        msisdn = "";
        gender = "";
        CRBStatus = 0;
        loanLimit = null;
    }

    public String getNationalID() {
        return nationalID;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getMiddleName() {
        return middleName;
    }
    
    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public int getCRBStatus() {
        return CRBStatus;
    }
    
    public BigDecimal getLoanLimit() {
        return loanLimit;
    }
    
    
    
    public void setLoanLimit( BigDecimal loanLimit ) {
        this.loanLimit=loanLimit;
    }
    
    public void setNationalID( String nationalID ) {
        this.nationalID = nationalID;
    }

    public void setMsisdn( String msisdn) {
        this.msisdn = msisdn;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }
    
    public void setMiddleName( String middleName) {
        this.middleName = middleName;
    }
    public void setSurname( String surname) {
        this.surname = surname;
    }

    

    public void setCustomerID( int CustomerID ) {
        this.CustomerID = CustomerID;
    }

    public void setCRBStatus( int CRBStatus ) {
        this.CRBStatus = CRBStatus;
    }
}
