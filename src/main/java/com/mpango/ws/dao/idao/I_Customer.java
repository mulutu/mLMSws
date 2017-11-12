/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.ws.dao.idao;

import com.mpango.ws.bus.CustomerDTO;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public interface I_Customer {

    CustomerDTO CustomerLogin(String MSISDN, String password, Connection con);

    CustomerDTO getCustomerByID(String IDNumber, Connection con);

    CustomerDTO getCustomerByMSISDN(String MSISDN, Connection con);
    
}
