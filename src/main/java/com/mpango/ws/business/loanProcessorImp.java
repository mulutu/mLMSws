/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.apache.log4j.Logger;

/**
 *
 * @author jmulutu
 */
public class loanProcessorImp {

    static Logger logger = Logger.getLogger(loanProcessorImp.class);

    public List<loginRequestResponse> loginRequest(String customerMSISDN, String password) {

        List<loginRequestResponse> output = new ArrayList<>();

        logger.info("--------------------------Starting loginRequest-----------------------------");

        try {

            Socket s = new Socket("localhost", 5555);

            OutputStream outToServer = s.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("LOGIN|" + customerMSISDN + "|" + password + "");

            InputStream inFromServer = s.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String data = in.readUTF();
            String delims = "[|]";
            String[] loginResponse = data.split(delims);

            String loginResponseCode = loginResponse[0];
            String loginResponseDesc = loginResponse[1];

            if (loginResponseCode.equalsIgnoreCase("00")) {
                logger.info(":: login success. ");
            }

            out.flush();
            out.close();
            //s.shutdownOutput();
            s.close();

            output.add(new loginRequestResponse(loginResponseCode, loginResponseDesc));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(loanProcessorImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public List<LoanRequestResponse> requestLoan(String customerMSISDN, BigDecimal loanAmount, int RepaymentPeriod, int LoanTypeID) {

        logger.info("--------------------------Starting requestLoan-------------------------------------");

        List<LoanRequestResponse> output = new ArrayList<>();
        IdGenerator randomGen = new IdGenerator();
        String formattedAmount = this.formatAmount(loanAmount);
        int transactionTypeID = 1; // Loan application        
        String processingCode = "400000";
        String ISOMTI = "0200";

        if (transactionTypeID == 1) {
            String transactionRef = "LR" + randomGen.generateId(5);
            try {
                ISOPackager p = new GenericPackager("cfg/packager/iso87ascii.xml");
                ISOMsg m = new ISOMsg();
                m.setPackager(p);
                //m.setHeader("ISO016000055".getBytes());
                m.setMTI(ISOMTI);
                m.set("2", customerMSISDN);
                m.set("3", processingCode);
                m.set("4", formattedAmount);
                m.set("9", Integer.toString(RepaymentPeriod));
                m.set("18", "0001"); // 18 ==lenth 4 ; transaction/loan type
                m.set("37", transactionRef);
                //m.set("42", "IDNUMBER"); //CustomerIDNumber);

                ISOChannel channel = new ASCIIChannel("localhost", 9800, p);

                channel.connect();
                channel.send(m);
                ISOMsg r = channel.receive();
                channel.disconnect();

                if (r.hasField(39)) {
                    String field39 = (String) r.getValue(39);
                    String field44 = (String) r.getValue(44);
                    output.add(new LoanRequestResponse(field39, field44));
                } else {
                    output.add(new LoanRequestResponse("99", "ERROR OCCURRED"));
                }

            } catch (IOException | ISOException ex) {
                output.add(new LoanRequestResponse("99", "ERROR OCCURRED"));
                //ex.printStackTrace();
                logger.error("======================================ERROR=======================================");
                logger.error(" :: Some exceptions founded.");
                logger.error(" :: Probably no connection to the LMS Server was found. Check status os LMS Server");
                logger.error("==================================================================================");
                logger.error((Supplier<String>) ex);
            }
        } else {
            output.add(new LoanRequestResponse("89", "ERROR OCCURRED"));
            logger.error("======================================ERROR=======================================");
            logger.error("ERROR: LOAN APPLICATION FAILED. pLEASE TRY AGAIN");
            logger.error("==================================================================================");
        }

        return output;
    }

    public List<LoanBalanceResponse> requestLoanBalance(String customerMSISDN, String loanNumber) {

        List<LoanBalanceResponse> output = new ArrayList<>();

        IdGenerator randomGen = new IdGenerator();

        int transactionTypeID = 3; // Loan balance        
        String processingCode = "200000";
        String ISOMTI = "0200";

        if (transactionTypeID == 3) {
            String transactionRef = "LB" + randomGen.generateId(5);

            try {
                ISOPackager p = new GenericPackager("cfg/packager/iso87ascii.xml");
                ISOMsg m = new ISOMsg();
                m.setPackager(p);
                //m.setHeader("ISO016000055".getBytes());
                m.setMTI(ISOMTI);
                m.set("2", customerMSISDN);
                m.set("3", processingCode);
                m.set("37", transactionRef);
                m.set("42", loanNumber);

                ISOChannel channel = new ASCIIChannel("localhost", 9800, p);

                channel.connect();
                channel.send(m);
                ISOMsg r = channel.receive();
                channel.disconnect();

                if (r.hasField(39)) {

                    String field7 = (String) r.getValue(7);
                    String field39 = (String) r.getValue(39);
                    String field44 = (String) r.getValue(44);
                    output.add(new LoanBalanceResponse(loanNumber, field44, field7));
                } else {
                    output.add(new LoanBalanceResponse(loanNumber, "ERROR", "today"));
                }

            } catch (IOException | ISOException ex) {
                output.add(new LoanBalanceResponse(loanNumber, "ERROR", "today"));
                //ex.printStackTrace();
                logger.error("======================================ERROR=======================================");
                logger.error(" :: Some exceptions founded.");
                logger.error(" :: Probably no connection to the LMS Server was found. Check status os LMS Server");
                logger.error("==================================================================================");
                logger.error((Supplier<String>) ex);
            }

        } else {
            output.add(new LoanBalanceResponse("99", "ERROR", "Error occured"));
            logger.error("Error. Technical glitch.");
        }

        return output;
    }

    public List<LoanRepayResponse> requestLoanRepayment(String customerMSISDN, String LoanRef, BigDecimal repaymentAmount) {
        logger.info("");
        logger.info("--------------------------Starting requestLoanRepayment-------------------------------------");
        logger.info("Customer MSISDN: " + customerMSISDN);

        List<LoanRepayResponse> output = new ArrayList<>();

        int transactionTypeID = 2; // Loan repayment        
        String processingCode = "300000";
        String ISOMTI = "0200";
        String formattedAmount = this.formatAmount(repaymentAmount);

        if (transactionTypeID == 2) {

            IdGenerator randomGen = new IdGenerator();
            String transactionRef = "LP" + randomGen.generateId(5);

            try {
                ISOPackager p = new GenericPackager("cfg/packager/iso87ascii.xml");
                ISOMsg m = new ISOMsg();
                m.setPackager(p);
                //m.setHeader("ISO016000055".getBytes());
                m.setMTI(ISOMTI);
                m.set("2", customerMSISDN);
                m.set("3", processingCode);
                m.set("4", formattedAmount);
                m.set("37", transactionRef);
                m.set("42", LoanRef);

                ISOChannel channel = new ASCIIChannel("localhost", 9800, p);

                channel.connect();
                channel.send(m);
                ISOMsg r = channel.receive();
                channel.disconnect();

                if (r.hasField(39)) {
                    if ("00".equals(r.getValue(39))) {
                        //TransactionBUS.updateTransactionStatus(trLogResult.getTransactionID(), "PROCESSED - APPLICATION SUCCESS", con);
                        //logger.info("Loan repayment successful: " + LoanRef);
                    } else {
                        //TransactionBUS.updateTransactionStatus(trLogResult.getTransactionID(), "PROCESSED - APPLICATION FAILED", con);
                        //logger.info("Loan repayment failed: " + LoanRef);
                    }
                    String field37 = (String) r.getValue(37);
                    String field39 = (String) r.getValue(39);
                    String field44 = (String) r.getValue(44);
                    output.add(new LoanRepayResponse(field39, field44, field37));
                } else {
                    output.add(new LoanRepayResponse("99", "ERROR", "today"));
                }

            } catch (IOException | ISOException ex) {
                output.add(new LoanRepayResponse("99", "ERROR", "today"));
                //ex.printStackTrace();
                logger.error("======================================ERROR=======================================");
                logger.error(" :: Some exceptions founded.");
                logger.error(" :: Probably no connection to the LMS Server was found. Check status os LMS Server");
                logger.error("==================================================================================");
            }

        } else {
            output.add(new LoanRepayResponse("99", "Technical error occured. Wrong transaction type", "Please try again.."));
            logger.error("Technical error occured. Wrong transaction type.");
        }
        return output;
    }

    private String formatAmount(BigDecimal loanAmount) {

        //loanAmount = loanAmount.multiply(new BigDecimal(100)); // make to cents
        //loanAmount = loanAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
        String formattedAmount = StringUtils.leftPad(loanAmount.toString(), 12, '0');

        return formattedAmount;
    }
}
