/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.ws.dao.idao;

import com.mpango.ws.bus.TransactionDTO;
import com.mpango.ws.business.Transaction;
import java.sql.Connection;

/**
 *
 * @author jmulutu
 */
public interface I_Transaction {

    int createTransaction(Transaction trx, Connection con);

    TransactionDTO getTransactionByID(int transactionID, Connection con);

    TransactionDTO getTransactionByTxRef(String trxRef, Connection con);

    void updateTransactionStatus(int transactionID, String status, Connection con);

}
