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
@XmlRootElement(name = "LoginRequestResponse")
@XmlType(propOrder = {"responseCode", "response"})
public class loginRequestResponse {

    private String responseCode;
    private String response;
    
    public loginRequestResponse() {
    }
    
    public loginRequestResponse(String responseCode, String response) {
        this.responseCode = responseCode;
        this.response = response;
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

    public void setresponse(String response) {
        this.response = response;
    }

}
