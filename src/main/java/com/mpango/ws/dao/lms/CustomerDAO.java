/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.ws.dao.lms;

import com.mpango.ws.dao.idao.I_Customer;
import com.mpango.ws.bus.CustomerDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jmulutu
 */
public class CustomerDAO implements I_Customer {
    
    @Override
    public CustomerDTO getCustomerByID( String IDNumber, Connection con ){
        CustomerDTO customer = new CustomerDTO();
        
        String SQL = "select * from customers where ID_NUMBER = '" + IDNumber + "'";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {                
                customer.setNationalID(rs.getString("ID_NUMBER"));
                customer.setCRBStatus(0);
                customer.setCustomerID(rs.getInt("CUSTOMER_ID"));                
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setMiddleName(rs.getString("MIDDLE_NAME"));
                customer.setSurname(rs.getString("SURNAME"));                
                customer.setMsisdn(rs.getString("MSISDN"));
                customer.setLoanLimit(rs.getBigDecimal("LOAN_LIMIT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
    
    @Override
    public CustomerDTO getCustomerByMSISDN( String MSISDN, Connection con ){
        CustomerDTO customer = new CustomerDTO();
        
        String SQL = "select * from customers where MSISDN = '" + MSISDN + "'";
        Statement st;
        try {
            st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {                
                customer.setNationalID(rs.getString("ID_NUMBER"));
                customer.setCRBStatus(0);
                customer.setCustomerID(rs.getInt("CUSTOMER_ID"));                
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setMiddleName(rs.getString("MIDDLE_NAME"));
                customer.setSurname(rs.getString("SURNAME"));                
                customer.setMsisdn(rs.getString("MSISDN"));
                customer.setLoanLimit(rs.getBigDecimal("LOAN_LIMIT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
    
    @Override
    public CustomerDTO CustomerLogin( String MSISDN, String password, Connection con ){
        CustomerDTO customer = new CustomerDTO();
        
        String SQL = "select * from customers where MSISDN = '" + MSISDN + "' AND password = '" + password + "'";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {                
                customer.setNationalID(rs.getString("ID_NUMBER"));
                customer.setCRBStatus(0);
                customer.setCustomerID(rs.getInt("CUSTOMER_ID"));                
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setMiddleName(rs.getString("MIDDLE_NAME"));
                customer.setSurname(rs.getString("SURNAME"));                
                customer.setMsisdn(rs.getString("MSISDN"));
                customer.setLoanLimit(rs.getBigDecimal("LOAN_LIMIT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
    
}
