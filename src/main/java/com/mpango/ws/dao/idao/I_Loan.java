/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mpango.ws.dao.idao;

import com.mpango.ws.bus.LoanDTO;
import com.mpango.ws.business.LoanApplication;
import java.math.BigDecimal;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public interface I_Loan {

    LoanDTO getLoanByID(int loanID, Connection con);

    LoanDTO getLoanByRef(String loanRef, Connection con);
    
    int loanApplication ( LoanApplication loanapplication, Connection con );   
    
}
