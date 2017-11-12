/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.dao.factory;

import com.mpango.ws.dao.idao.I_Customer;
import com.mpango.ws.dao.idao.I_Loan;
import com.mpango.ws.dao.lms.CustomerDAO;
import com.mpango.ws.dao.idao.I_Transaction;
import com.mpango.ws.dao.lms.LoanDAO;
import com.mpango.ws.dao.lms.TransactionDAO;

/**
 *
 * @author jmulutu
 */
public class SqlDAO extends DAOFactory {

    @Override
    public I_Customer get_Customer() {
        return new CustomerDAO();
    }

    @Override
    public I_Loan get_Loan() {
        return new LoanDAO();
    }
    
    @Override
    public I_Transaction get_Transaction() {
        return new TransactionDAO();
    }

}
