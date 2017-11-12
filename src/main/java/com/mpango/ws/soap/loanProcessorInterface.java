/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.soap;

import com.mpango.ws.business.LoanBalanceResponse;
import com.mpango.ws.business.LoanRepayResponse;
import com.mpango.ws.business.LoanRequestResponse;
import com.mpango.ws.business.loginRequestResponse;
import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author jmulutu
 */
@WebService(name = "loanProcessor", targetNamespace = "http://www.mpango.com")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface loanProcessorInterface {

    @WebMethod
    public abstract @WebResult(name = "LoanBalance")
    List<LoanBalanceResponse> loanBalanceRequest(
            @WebParam(name = "customerMSISDN", partName = "customerMSISDN") String customerMSISDN,
            @WebParam(name = "loanNumber", partName = "loanNumber") String loanNumber);

    @WebMethod(operationName = "loanApplicationRequest")
    public abstract @WebResult(name = "LoanApplicationResult")
    List<LoanRequestResponse> loanRequest(
            @WebParam(name = "customerMSISDN", partName = "customerMSISDN") String customerMSISDN,
            @WebParam(name = "loanAmount", partName = "loanAmount") BigDecimal loanAmount,
            @WebParam(name = "repaymentPeriod", partName = "repaymentPeriod") int RepaymentPeriod,
            @WebParam(name = "loanTypeID", partName = "loanTypeID") int LoanTypeID);

    @WebMethod(operationName = "LoanRepayRequest")
    public abstract @WebResult(name = "LoanRepayResult")
    List<LoanRepayResponse> requestLoanRepayment(
            @WebParam(name = "customerMSISDN", partName = "customerMSISDN") String CustomerMSISDN,
            @WebParam(name = "loanNumber", partName = "loanNumber") String LoanRef,
            @WebParam(name = "repaymentAmount", partName = "repaymentAmount") BigDecimal repaymentAmount);

    @WebMethod
    public abstract @WebResult(name = "LoginResult")
    List<loginRequestResponse> loginRequest(
            @WebParam(name = "customerMSISDN", partName = "customerMSISDN") String customerMSISDN,
            @WebParam(name = "password", partName = "password") String password);

}
