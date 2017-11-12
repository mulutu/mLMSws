/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.dao.factory;

import com.mpango.ws.dao.idao.I_Customer;
import com.mpango.ws.dao.idao.I_Loan;
import com.mpango.ws.dao.idao.I_Transaction;
import org.jpos.util.NameRegistrar;

/**
 *
 * @author jmulutu
 */
public abstract class DAOFactory {

    public abstract I_Customer get_Customer();

    public abstract I_Loan get_Loan();
    
    public abstract I_Transaction get_Transaction();
    

    /**
     * Get instance of class indicate for specific database..
     *
     * @return instance of class indicate for specific database.
     */
    public static DAOFactory getInstances() {
        //default SQL server
        return new SqlDAO();
    }

}
