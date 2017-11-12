/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.soap;

import com.mpango.ws.business.LoanBalanceResponse;
import com.mpango.ws.business.LoanRepayResponse;
import com.mpango.ws.business.LoanRequestResponse;
import com.mpango.ws.business.loanProcessorImp;
import com.mpango.ws.business.loginRequestResponse;
import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author jmulutu
 */
@WebService(endpointInterface = "com.mpango.ws.soap.loanProcessorInterface", portName = "loanProcessorPort", serviceName = "loanProcessor")
public class loanProcessor implements loanProcessorInterface {

    loanProcessorImp loanProcess = new loanProcessorImp();
    loanProcessorImp loanBalance = new loanProcessorImp();
    loanProcessorImp loginProcess = new loanProcessorImp();
    loanProcessorImp loanRepayment = new loanProcessorImp();

    @Override
    public List<LoanRequestResponse> loanRequest(String customerMSISDN, BigDecimal loanAmount, int RepaymentPeriod, int LoanTypeID) {
        return loanProcess.requestLoan(customerMSISDN, loanAmount, RepaymentPeriod, LoanTypeID);
    }

    @Override
    public List<LoanBalanceResponse> loanBalanceRequest(String customerMSISDN, String loanNumber) {
        return loanBalance.requestLoanBalance(customerMSISDN, loanNumber);
    }

    @Override
    public List<LoanRepayResponse> requestLoanRepayment( String CustomerMSISDN, String LoanRef, BigDecimal repaymentAmount) {
        return loanRepayment.requestLoanRepayment( CustomerMSISDN, LoanRef, repaymentAmount);
    }

    @Override
    public List<loginRequestResponse> loginRequest(String customerMSISDN, String password) {
        return loginProcess.loginRequest(customerMSISDN, password);
    }

}
