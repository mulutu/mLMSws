/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.dao.lms;

import com.mpango.ws.dao.idao.I_Loan;
import com.mpango.ws.bus.LoanDTO;
import com.mpango.ws.business.LoanApplication;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jmulutu
 */
public class LoanDAO implements I_Loan {

    @Override
    public LoanDTO getLoanByID(int loanID, Connection con) {
        LoanDTO loan = new LoanDTO();

        String SQL = "select * from loan_disbursements where ID = " + loanID + "";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                loan.setLoanID(rs.getInt("ID"));
                loan.setLoanRef(rs.getString("LOAN_REF"));
                loan.setDateDisbursed(rs.getString("DATE_DISBURSED"));
                loan.setDueDate(rs.getString("DUE_DATE"));
                loan.setAmount(rs.getBigDecimal("PRINCIPAL_AMOUNT"));
                loan.setRepaymentDuration(rs.getInt("LOAN_TERM"));
                loan.setLoanBalance(rs.getBigDecimal("LOAN_BALANCE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    @Override
    public LoanDTO getLoanByRef(String loanRef, Connection con) {
        LoanDTO loan = new LoanDTO();
        String SQL = "select * from loan_disbursements where LOAN_REF = '" + loanRef + "'";
        Statement st;
        try {
            st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                loan.setLoanID(rs.getInt("ID"));
                loan.setLoanRef(rs.getString("LOAN_REF"));
                loan.setDateDisbursed(rs.getString("DATE_DISBURSED"));
                loan.setDueDate(rs.getString("DUE_DATE"));
                loan.setAmount(rs.getBigDecimal("PRINCIPAL_AMOUNT"));
                loan.setRepaymentDuration(rs.getInt("LOAN_TERM"));
                loan.setLoanBalance(rs.getBigDecimal("LOAN_BALANCE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loan;
    }

    @Override
    public int loanApplication(LoanApplication loanapplication, Connection con) {
        //LoanDTO loan = null;
        int result = 0;

        String SQL = "INSERT INTO `loan_applications`( `CUSTOMER_ID`, `TRX_REF_NUMBER`, `LOAN_REF`, `LOAN_TYPE_ID`, `AMOUNT`, `DURATION`, `REQUEST_DATE` ) "
                + "VALUES ( "
                + "" + loanapplication.getCustomerID() + ","
                + "'" + loanapplication.getTransactionRef() + "',"
                + "'" + loanapplication.getLoanRef() + "',"
                + "" + loanapplication.getLoanTypeID() + ","
                + "" + loanapplication.getAmount() + ","
                + "" + loanapplication.getRepaymentDuration() + ","
                + "NOW() "
                + ")";

        Statement st;
        try {
            //st = con.createStatement();
            st = con.prepareStatement(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                //loan = this.getLoanByRef(loanapplication.getLoanRef(), con);
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
