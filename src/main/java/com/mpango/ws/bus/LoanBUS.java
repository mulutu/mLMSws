/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.bus;

import com.mpango.ws.business.LoanApplication;
import com.mpango.ws.dao.factory.DAOFactory;
import com.mpango.ws.dao.idao.I_Loan;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public class LoanBUS {

    public static LoanDTO getLoanByID(int loanID, Connection con) {
        DAOFactory factory = DAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanByID(loanID, con);
    }

    public static LoanDTO getLoanByRef(String loanRef, Connection con) {
        DAOFactory factory = DAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.getLoanByRef(loanRef, con);
    }

    public static int loanApplication(LoanApplication loanapplication, Connection con) {
        DAOFactory factory = DAOFactory.getInstances();
        I_Loan myLoan = factory.get_Loan();
        return myLoan.loanApplication(loanapplication, con);
    }
}
