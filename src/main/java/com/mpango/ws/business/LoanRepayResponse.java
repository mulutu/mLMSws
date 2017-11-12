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
@XmlRootElement(name = "LoanRepayResponse")
@XmlType(propOrder = {"responseCode", "response", "metaData"})
public class LoanRepayResponse {

    private String responseCode;
    private String response;
    private String metaData;

    public LoanRepayResponse() {
    }
    
    public LoanRepayResponse( String responseCode, String response, String metaData){
        this.responseCode=responseCode;
        this.response=response;
        this.metaData=metaData;
    }

    /**
     * @return the responseCode
     */
    @XmlElement(name = "ResponseCode")
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @return the response
     */
    @XmlElement(name = "Response")
    public String getResponse() {
        return response;
    }

    /**
     * @return the metaData
     */
    @XmlElement(name = "MetaData")
    public String getMetaData() {
        return metaData;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @param metaData the metaData to set
     */
    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

}
