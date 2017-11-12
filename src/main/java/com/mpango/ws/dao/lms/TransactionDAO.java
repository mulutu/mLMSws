/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.dao.lms;

import com.mpango.ws.dao.idao.I_Transaction;
import com.mpango.ws.bus.TransactionDTO;
import com.mpango.ws.business.Transaction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jmulutu
 */
public class TransactionDAO implements I_Transaction {

    @Override
    public TransactionDTO getTransactionByID(int transactionID, Connection con) {
        TransactionDTO transaction = new TransactionDTO();

        String SQL = "select * from tr_log where TRX_ID = " + transactionID + "";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                transaction.setTransactionID(rs.getInt("TRX_ID"));
                transaction.setTransactionRefNum(rs.getString("TRX_REF_NUMBER"));
                transaction.setCustomerID(rs.getInt("CUSTOMER_ID"));
                transaction.setTransactionTypeID(rs.getInt("TRX_TYPE_ID"));
                transaction.setDateLogged(rs.getString("DATE_LOGGED"));
                transaction.setStatus(rs.getString("STATUS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    @Override
    public TransactionDTO getTransactionByTxRef(String trxRef, Connection con) {
        TransactionDTO transaction = new TransactionDTO();

        String SQL = "select * from tr_log where TRX_REF_NUMBER = '" + trxRef + "'";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                transaction.setTransactionID(rs.getInt("TRX_ID"));
                transaction.setTransactionRefNum(rs.getString("TRX_REF_NUMBER"));
                transaction.setCustomerID(rs.getInt("CUSTOMER_ID"));
                transaction.setTransactionTypeID(rs.getInt("TRX_TYPE_ID"));
                transaction.setDateLogged(rs.getString("DATE_LOGGED"));
                transaction.setStatus(rs.getString("STATUS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    @Override
    public int createTransaction(Transaction trx, Connection con) {
        //TransactionDTO transaction = null;
        int result = 0;
        String SQL = "INSERT INTO `tr_log`( `TRX_REF_NUMBER`, `CUSTOMER_ID`, `TRX_TYPE_ID`, `DATE_LOGGED` ) "
                + "VALUES ( "
                + "'" + trx.getTransactionRefNum() + "',"
                + "" + trx.getCustomerID() + ","
                + "" + trx.getTransactionTypeID() + ","
                + " NOW()"
                + ")";
        Statement st;
        try {
            //st = con.createStatement();
            st = con.prepareStatement(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                //transaction = this.getTransactionByTxRef(trx.getTransactionRefNum(), con);
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateTransactionStatus(int transactionID, String status, Connection con) {

        String SQL = "UPDATE `tr_log` SET status = '" + status + "' WHERE TRX_ID=" + transactionID + "";
        Statement st;
        try {
            st = con.prepareStatement(SQL);
            int rowsUpdated = st.executeUpdate(SQL);
            if (rowsUpdated > 0) {
                //output = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
