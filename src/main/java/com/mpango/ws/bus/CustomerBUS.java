/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.bus;

import com.mpango.ws.dao.factory.DAOFactory;
import com.mpango.ws.dao.idao.I_Customer;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public class CustomerBUS {

    public static CustomerDTO getCustomerByID(String IDNumber, Connection con) {
        DAOFactory factory = DAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.getCustomerByID(IDNumber, con);
    }

    public static CustomerDTO getCustomerByMSISDN(String MSISDN, Connection con) {
        DAOFactory factory = DAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.getCustomerByMSISDN(MSISDN, con);
    }

    public static CustomerDTO customerLogin(String MSISDN, String password, Connection con) {
        DAOFactory factory = DAOFactory.getInstances();
        I_Customer myCustomer = factory.get_Customer();
        return myCustomer.CustomerLogin(MSISDN, password, con);
    }
}
