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
@XmlRootElement(name = "LoanRequestResponse")
@XmlType(propOrder = {"responseCode", "response"})
public class LoanRequestResponse {

    private String responseCode;
    private String response;

    
    public LoanRequestResponse() {
    }

    
    public LoanRequestResponse(String responseCode, String value) {
        this.responseCode = responseCode;
        this.response = value;
    }

    @XmlElement(name = "responseCode")
    public String getresponseCode() {
        return responseCode;
    }

    @XmlElement(name = "responseValue")
    public String getresponse() {
        return response;
    }

    public void setresponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setresponse(String value) {
        this.response = value;
    }

}
