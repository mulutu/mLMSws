/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.ussd;

/**
 *
 * @author jmulutu
 */
public class MoUssdResp {

    public MoUssdResp() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public String toString() {
        return (new StringBuilder()).append("MoUssdResp{").append("statusCode='").append(statusCode).append('\'').append(", statusDetail='").append(statusDetail).append('\'').append('}').toString();
    }

    private String statusCode;
    private String statusDetail;
}
